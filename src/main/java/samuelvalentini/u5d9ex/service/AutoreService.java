package samuelvalentini.u5d9ex.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import samuelvalentini.u5d9ex.dto.AutorePayload;
import samuelvalentini.u5d9ex.dto.UpdateAutorePayload;
import samuelvalentini.u5d9ex.entity.Autore;
import samuelvalentini.u5d9ex.exception.BadRequestException;
import samuelvalentini.u5d9ex.exception.NotFoundException;
import samuelvalentini.u5d9ex.repository.AutoreRepository;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
public class AutoreService {
    private final AutoreRepository autoreRepository;
    private final Cloudinary cloudinary;

    public AutoreService(AutoreRepository autoreRepository, Cloudinary cloudinary) {
        this.autoreRepository = autoreRepository;
        this.cloudinary = cloudinary;
    }

    public Page<Autore> findAll(@PositiveOrZero int start) {
        Pageable pageable = PageRequest.of(start, 3, Sort.by("cognome").ascending().and(Sort.by("nome").ascending()));
        return autoreRepository.findAll(pageable);
    }

    public Autore saveNewAutore(AutorePayload autorePayload) {
        Autore autore = new Autore(autorePayload.getNome(), autorePayload.getCognome(), autorePayload.getEmail(), autorePayload.getDataDiNascita());
        return this.autoreRepository.save(autore);
    }

    public Autore findById(long autoreId) {
        return autoreRepository.findById(autoreId).orElseThrow(() -> new NotFoundException(String.valueOf(autoreId)));
    }

    public void deleteAutore(long autoreId) {
        Autore found = autoreRepository.findById(autoreId).orElseThrow(() -> new NotFoundException(String.valueOf(autoreId)));
        String nome = found.getNome();
        String cognome = found.getCognome();
        autoreRepository.delete(found);
        log.info("Autore {} {} rimosso", nome, cognome);
    }

    public Autore findByIdAndUpdate(long autoreId, UpdateAutorePayload updateAutorePayload) {
        Autore found = autoreRepository.findById(autoreId).orElseThrow(() -> new NotFoundException(String.valueOf(autoreId)));
        found.setNome(updateAutorePayload.getNome());
        found.setCognome(updateAutorePayload.getCognome());
        found.setEmail(updateAutorePayload.getEmail());
        found.setDataDiNascita(updateAutorePayload.getDataDiNascita());
        found.setAvatar(updateAutorePayload.getAvatar());
        return autoreRepository.save(found);
    }

    public Autore avatarUpload(@NotNull Long autoreId, MultipartFile file) {
        if (autoreId == null) throw new BadRequestException("Id non inserito");
        if (file.isEmpty()) throw new BadRequestException("File non inserito correttamente");
        if (file.getSize() > 5_242_880)
            throw new BadRequestException("File troppo grande, il file deve essere inferiore a 5 Mb");
        if (file.getContentType() == null || !file.getContentType().startsWith("image/"))
            throw new BadRequestException("Il file deve essere un'immagine");
        try {
            BufferedImage image = ImageIO.read(file.getInputStream());
        } catch (IOException e) {
            throw new BadRequestException("Immagine illeggibile");
        }
        Autore autore = this.findById(autoreId);

        try {
            Map result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String url = (String) result.get("secure_url");
            autore.setAvatar(url);
            this.autoreRepository.save(autore);
            return autore;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
