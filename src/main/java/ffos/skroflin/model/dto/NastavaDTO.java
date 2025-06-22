/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ffos.skroflin.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author svenk
 */
public record NastavaDTO(
        @Schema(example = "2024-10-01") Date datumNastave,
        @Schema(example = "Uvod u programiranje 1") String naslovPredavanje,
        @Schema(example = "100.50") BigDecimal cijena,
        @Schema(example = "1") int grupaSifra
        ) {
    
}
