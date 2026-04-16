package samuelvalentini.u5d9ex.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import samuelvalentini.u5d9ex.dto.BlogPayload;
import samuelvalentini.u5d9ex.dto.UpdateBlogPayload;
import samuelvalentini.u5d9ex.entity.Blog;
import samuelvalentini.u5d9ex.exception.NotFoundException;
import samuelvalentini.u5d9ex.repository.BlogRepository;

import java.util.List;

@Service
@Slf4j
public class BlogService {
    private final BlogRepository blogRepository;
    private final AutoreService autoreService;

    public BlogService(BlogRepository blogRepository, AutoreService autoreService) {
        this.blogRepository = blogRepository;
        this.autoreService = autoreService;
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

}
