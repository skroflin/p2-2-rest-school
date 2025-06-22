/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ffos.skroflin.controller;

import ffos.skroflin.model.Godina;
import ffos.skroflin.model.Grupa;
import ffos.skroflin.model.dto.GrupaDTO;
import ffos.skroflin.service.GodinaService;
import ffos.skroflin.service.GrupaService;
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
@RequestMapping("/api/skroflin/grupa")
public class GrupaController {
    private final GrupaService grupaService;
    private final GodinaService godinaService;

    public GrupaController(GrupaService grupaService, GodinaService godinaService) {
        this.grupaService = grupaService;
        this.godinaService = godinaService;
    }
    
    @Operation(
            summary = "Dohvaća sve grupe", tags = {"get", "grupa"},
            description = "Dohvaća sve prostorije sa svim podacima"
    )
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Grupa.class)))),
                @ApiResponse(responseCode = "500", description = "Interna pogreška servera", content = @Content(schema = @Schema(implementation = String.class), mediaType = "text/html"))
            })
    @GetMapping("/get")
    public ResponseEntity getAll(){
        try {
            return new ResponseEntity<>(grupaService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Operation(
            summary = "Dohvaća grupu po šifri",
            description = "Dohvaća grupu po danoj šifri sa svim podacima. "
            + "Ukoliko ne postoji grupa za danu šifru vraća prazan odgovor",
            tags = {"grupa", "getBy"},
            parameters = {
                @Parameter(
                        name = "sifra",
                        allowEmptyValue = false,
                        required = true,
                        description = "Primarni ključ grupe u bazi podataka, mora biti veći od nula",
                        example = "2"
                )})
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Grupa.class), mediaType = "application/json")),
        @ApiResponse(responseCode = "204", description = "Ne postoji grupa za danu šifru", content = @Content(schema = @Schema(implementation = String.class), mediaType = "text/html")),
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
            Grupa grupa = grupaService.getBySifra(sifra);
            if (grupa == null) {
                return new ResponseEntity("Grupa s navedenom šifrom ne postoji", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(grupa, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Operation(
            summary = "Kreira grupu",
            tags = {"grupa", "post"},
            description = "Kreira novu grupu. Ukoliko je unesena šifra godine koja ne postoji, vraća grešku!")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Kreirano", content = @Content(schema = @Schema(implementation = Grupa.class), mediaType = "application/json")),
        @ApiResponse(responseCode = "400", description = "Loš zahtjev (nije primljen dto objekt ili ne postoji godina!)", content = @Content(schema = @Schema(implementation = String.class), mediaType = "text/html")),
        @ApiResponse(responseCode = "500", description = "Interna pogreška servera", content = @Content(schema = @Schema(implementation = String.class), mediaType = "text/html"))
    })
    @PostMapping("/post")
    public ResponseEntity post(
            @RequestBody(required = true) GrupaDTO dto
    ){
        try {
            if (dto == null) {
                return new ResponseEntity<>("Nisu uneseni traženi podaci.", HttpStatus.NO_CONTENT);
            }
            Godina godina = godinaService.getBySifra(dto.godinaSifra());
            if (godina == null) {
                return new ResponseEntity<>("Godina s navedenom šifrom ne postoji", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(grupaService.post(dto), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
  
    }
}
