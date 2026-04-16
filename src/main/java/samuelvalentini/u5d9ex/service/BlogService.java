package samuelvalentini.u5d9ex.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import samuelvalentini.u5d9ex.dto.BlogPayload;
import samuelvalentini.u5d9ex.dto.UpdateBlogPayload;
import samuelvalentini.u5d9ex.entity.Blog;
import samuelvalentini.u5d9ex.exception.BadRequestException;
import samuelvalentini.u5d9ex.exception.NotFoundException;
import samuelvalentini.u5d9ex.repository.BlogRepository;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class BlogService {
    private final BlogRepository blogRepository;
    private final AutoreService autoreService;
    private final Cloudinary cloudinary;

    public BlogService(BlogRepository blogRepository, AutoreService autoreService, Cloudinary cloudinary) {
        this.blogRepository = blogRepository;
        this.autoreService = autoreService;
        this.cloudinary = cloudinary;
    }

    public List<Blog> findAll() {
        return blogRepository.findAll();
    }

    public Blog findById(long blogId) {
        return blogRepository.findById(blogId).orElseThrow(() -> new NotFoundException(String.valueOf(blogId)));
    }

    public Blog saveNewBlog(BlogPayload blogPayload) {
        Blog blog = new Blog(blogPayload.getCategoria(), blogPayload.getTitolo(), blogPayload.getContenuto(), autoreService.findById(blogPayload.getAutoreId()));
        return this.blogRepository.save(blog);
    }

    public Blog findByIdAndUpdate(long blogId, UpdateBlogPayload updateBlogPayload) {
        Blog found = blogRepository.findById(blogId).orElseThrow(() -> new NotFoundException(String.valueOf(blogId)));
        found.setCategoria(updateBlogPayload.getCategoria());
        found.setTitolo(updateBlogPayload.getTitolo());
        found.setContenuto(updateBlogPayload.getContenuto());
        found.setCover(updateBlogPayload.getCover());
        found.setTempoDiLettura(updateBlogPayload.getTempoDiLettura());
        return found;
    }

    public void deleteBlog(long blogId) {
        this.blogRepository.delete(blogRepository.findById(blogId).orElseThrow(() -> new NotFoundException(String.valueOf(blogId))));
    }

    public Blog updateBlogCover(Long blogId, MultipartFile file) {
        if (blogId == null) throw new BadRequestException("Id non inserito");
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
        Blog blog = this.findById(blogId);
        try {
            Map result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String url = (String) result.get("secure_url");
            blog.setCover(url);
            this.blogRepository.save(blog);
            return blog;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
