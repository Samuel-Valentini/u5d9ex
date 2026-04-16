package samuelvalentini.u5d9ex.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import samuelvalentini.u5d9ex.dto.AutorePayload;
import samuelvalentini.u5d9ex.dto.UpdateAutorePayload;
import samuelvalentini.u5d9ex.entity.Autore;
import samuelvalentini.u5d9ex.exception.BadRequestException;
import samuelvalentini.u5d9ex.service.AutoreService;

@RestController
@RequestMapping("/authors")
public class AutoreController {

    private final AutoreService autoreService;

    public AutoreController(AutoreService autoreService) {
        this.autoreService = autoreService;
    }

    @GetMapping
    public Page<Autore> findAll(@RequestParam(defaultValue = "0") String start) {
        int startNumber;
        try {
            startNumber = Integer.parseInt(start);
        } catch (NumberFormatException e) {
            throw new BadRequestException("Non è stato inserito un numero intero maggiore o uguale a 0.");
        }
        return autoreService.findAll(startNumber);
    }

    @GetMapping("/{autoreId}")
    public Autore findById(@PathVariable long autoreId) {
        return autoreService.findById(autoreId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Autore createNewAutore(@RequestBody AutorePayload autorePayload) {
        return autoreService.saveNewAutore(autorePayload);
    }

    @PutMapping("/{autoreId}")
    public Autore updateAutore(@PathVariable long autoreId, @RequestBody UpdateAutorePayload updateAutorePayload) {
        return this.autoreService.findByIdAndUpdate(autoreId, updateAutorePayload);
    }

    @DeleteMapping("/{autoreId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAutore(@PathVariable long autoreId) {
        autoreService.deleteAutore(autoreId);
    }

}
