package samuelvalentini.u5d9ex.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import samuelvalentini.u5d9ex.dto.AutorePayload;
import samuelvalentini.u5d9ex.dto.UpdateAutorePayload;
import samuelvalentini.u5d9ex.entity.Autore;
import samuelvalentini.u5d9ex.exception.NotFoundException;
import samuelvalentini.u5d9ex.repository.AutoreRepository;

@Service
@Slf4j
public class AutoreService {
    private final AutoreRepository autoreRepository;

    public AutoreService(AutoreRepository autoreRepository) {
        this.autoreRepository = autoreRepository;
    }

    public Page<Autore> findAll(int start) {
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
}
