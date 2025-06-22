/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ffos.skroflin.controller;

import ffos.skroflin.model.Grupa;
import ffos.skroflin.model.Nastava;
import ffos.skroflin.model.dto.NastavaDTO;
import ffos.skroflin.service.GrupaService;
import ffos.skroflin.service.NastavaService;
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
@RequestMapping("/api/skroflin/nastava")
public class NastavaController {
    private final NastavaService nastavaService;
    private final GrupaService grupaService;

    public NastavaController(NastavaService nastavaService, GrupaService grupaService) {
        this.nastavaService = nastavaService;
        this.grupaService = grupaService;
    }
    
    @Operation(
            summary = "Dohvaća sve nastave", tags = {"get", "nastava"},
            description = "Dohvaća sve nastave sa svim podacima"
    )
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Nastava.class)))),
                @ApiResponse(responseCode = "500", description = "Interna pogreška servera", content = @Content(schema = @Schema(implementation = String.class), mediaType = "text/html"))
            })
    @GetMapping("/get")
    public ResponseEntity getAll(){
        try {
            return new ResponseEntity<>(nastavaService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Operation(
            summary = "Dohvaća nastavu po šifri",
            description = "Dohvaća nastavu po danoj šifri sa svim podacima. "
            + "Ukoliko ne postoji nastava za danu šifru vraća prazan odgovor",
            tags = {"nastava", "getBy"},
            parameters = {
                @Parameter(
                        name = "sifra",
                        allowEmptyValue = false,
                        required = true,
                        description = "Primarni ključ nastave u bazi podataka, mora biti veći od nula",
                        example = "2"
                )})
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Nastava.class), mediaType = "application/json")),
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
            Nastava nastava = nastavaService.getBySifra(sifra);
            if (nastava == null) {
                return new ResponseEntity<>("Nastava s navedenom šifrom ne postoji", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(nastava, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Operation(
            summary = "Kreira novu nastavu",
            tags = {"nastava", "post"},
            description = "Kreira novu nastavu. Naslov predavanja je obavezan!"
                           + " Ukoliko je unesena šifra grupe koja ne postoji, vraća grešku!")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Kreirano", content = @Content(schema = @Schema(implementation = Nastava.class), mediaType = "application/json")),
        @ApiResponse(responseCode = "400", description = "Loš zahtjev (nije primljen dto objekt ili ne postoji maslov predavanja ili šifra grupe ne postoji u bazi)", content = @Content(schema = @Schema(implementation = String.class), mediaType = "text/html")),
        @ApiResponse(responseCode = "500", description = "Interna pogreška servera", content = @Content(schema = @Schema(implementation = String.class), mediaType = "text/html"))
    })
    @PostMapping("/post")
    public ResponseEntity post(
            @RequestBody(required = true) NastavaDTO dto
    ){
        try {
            if (dto == null) {
                return new ResponseEntity<>("Nisu uneseni traženi podaci.", HttpStatus.NO_CONTENT);
            }
            Grupa grupa = grupaService.getBySifra(dto.grupaSifra());
            if (grupa == null) {
                return new ResponseEntity<>("Grupa s navedenom šifrom ne postoji", HttpStatus.NOT_FOUND);
            }
            if (dto.naslovPredavanje() == null || dto.naslovPredavanje().isEmpty()) {
                return new ResponseEntity<>("Naslov predavanja je obavezan!", HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(nastavaService.post(dto), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
