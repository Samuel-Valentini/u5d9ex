package samuelvalentini.u5d9ex.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import samuelvalentini.u5d9ex.dto.BlogPayload;
import samuelvalentini.u5d9ex.dto.UpdateBlogPayload;
import samuelvalentini.u5d9ex.entity.Blog;
import samuelvalentini.u5d9ex.exception.BadRequestException;
import samuelvalentini.u5d9ex.service.BlogService;

import java.util.List;

@RestController
@RequestMapping("/blogPosts")
public class BlogController {
    private BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping
    public List<Blog> findAll() {
        return this.blogService.findAll();
    }

    @GetMapping("/{blogId}")
    public Blog getBlogById(@PathVariable long blogId) {
        return this.blogService.findById(blogId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Blog createNewBlog(@RequestBody BlogPayload blogPayload) {
        if (blogPayload == null || blogPayload.getTitolo() == null || blogPayload.getCategoria() == null || blogPayload.getContenuto() == null || blogPayload.getAutoreId() == null)
            throw new BadRequestException("Tutti i campi devono essere compilati");
        return this.blogService.saveNewBlog(blogPayload);
    }

    @PutMapping("/{blogId}")
    public Blog updateBlog(@PathVariable long blogId, @RequestBody UpdateBlogPayload updateBlogPayload) {
        return this.blogService.findByIdAndUpdate(blogId, updateBlogPayload);
    }

    @DeleteMapping("/{blogId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBlog(@PathVariable long blogId) {
        this.blogService.deleteBlog(blogId);
    }
}
