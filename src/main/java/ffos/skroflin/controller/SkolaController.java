/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ffos.skroflin.controller;

import ffos.skroflin.model.Skola;
import ffos.skroflin.model.dto.SkolaDTO;
import ffos.skroflin.service.SkolaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author svenk
 */
@RestController
@RequestMapping("/api/skroflin/skola")
public class SkolaController {
    private final SkolaService skolaService;

    public SkolaController(SkolaService skolaService) {
        this.skolaService = skolaService;
    }
    
    @Operation(
            summary = "Dohvaća sve škole", tags = {"get", "skola"},
            description = "Dohvaća sve škole sa svim podacima"
    )
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Skola.class)))),
                @ApiResponse(responseCode = "500", description = "Interna pogreška servera", content = @Content(schema = @Schema(implementation = String.class), mediaType = "text/html"))
            })
    @GetMapping("/get")
    public ResponseEntity getAll(){
        try {
            return new ResponseEntity<>(skolaService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Operation(
            summary = "Dohvaća školu po šifri",
            description = "Dohvaća školu po danoj šifri sa svim podacima. "
            + "Ukoliko ne postoji škola za danu šifru vraća prazan odgovor",
            tags = {"škola", "getBy"},
            parameters = {
                @Parameter(
                        name = "sifra",
                        allowEmptyValue = false,
                        required = true,
                        description = "Primarni ključ škole u bazi podataka, mora biti veći od nula",
                        example = "2"
                )})
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Skola.class), mediaType = "application/json")),
        @ApiResponse(responseCode = "204", description = "Ne postoji student za danu šifru", content = @Content(schema = @Schema(implementation = String.class), mediaType = "text/html")),
        @ApiResponse(responseCode = "400", description = "Šifra mora biti veća od nula", content = @Content(schema = @Schema(implementation = String.class), mediaType = "text/html")),
        @ApiResponse(responseCode = "500", description = "Interna pogreška servera", content = @Content(schema = @Schema(implementation = String.class), mediaType = "text/html"))
    })
    @GetMapping("/getBySifra")
    public ResponseEntity getBySifra(
            @RequestParam int sifra
    ){
        try {
            if (sifra <= 0) {
                return new ResponseEntity<>("Šifra ne smije biti manja od 0", HttpStatus.BAD_REQUEST);
            }
            Skola skola = skolaService.getBySifra(sifra);
            if (skola == null) {
                return new ResponseEntity<>("Škola s navedenom šifrom ne postoji", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(skola, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Operation(
            summary = "Kreira novu školu",
            tags = {"škola", "post"},
            description = "Kreira novu školu. Naziv škole je obavezna!")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Kreirano", content = @Content(schema = @Schema(implementation = Skola.class), mediaType = "application/json")),
        @ApiResponse(responseCode = "400", description = "Loš zahtjev (nije primljen dto objekt ili ne postoji naziv škole!)", content = @Content(schema = @Schema(implementation = String.class), mediaType = "text/html")),
        @ApiResponse(responseCode = "500", description = "Interna pogreška servera", content = @Content(schema = @Schema(implementation = String.class), mediaType = "text/html"))
    })
    @PostMapping("/post")
    public ResponseEntity post(
            @RequestBody(required = true) SkolaDTO dto
    ){
        try {
            if (dto == null) {
                return new ResponseEntity<>("Nisu uneseni obvezni podaci", HttpStatus.NO_CONTENT);
            }
            if (dto.naziv() == null || dto.naziv().isEmpty()) {
                return new ResponseEntity<>("Naziv škole je obavezan", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(skolaService.post(dto), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
