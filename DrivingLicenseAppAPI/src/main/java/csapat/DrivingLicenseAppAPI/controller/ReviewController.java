package csapat.DrivingLicenseAppAPI.controller;

import com.fasterxml.jackson.databind.JsonNode;
import csapat.DrivingLicenseAppAPI.entity.Review;
import csapat.DrivingLicenseAppAPI.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "Értékelések lekérdezése", description = "Értékelések lekérdezése az adott iskoláról vagy oktatóról")
    @Parameters({
            @Parameter(name = "about", description = "Azt határozza meg, hogy iskoláról vagy oktatóról szeretnénk lekérdezni az értékeléseket. Értéke csak instructor vagy school lehet.", required = true, in = ParameterIn.QUERY),
            @Parameter(name = "aboutId", description = "A vélemények forrásához tartozó id.", required = true, in = ParameterIn.QUERY)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres lekérdezés", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Review.class))
            )),
            @ApiResponse(responseCode = "404", description = "A vélemények forrása nem létezik", content = @Content),
            @ApiResponse(responseCode = "415", description = "Az about parameter nem egyenlő instructor-ral vagy school-al", content = @Content),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghívása parameter(ek) nélkül", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content),
    })
    @GetMapping("")
    public ResponseEntity<Object> getReviews(@RequestParam("about") String about, @RequestParam("aboutId") Integer aboutId) {
        return reviewService.getReviews(about, aboutId);
    }

    @Operation(summary = "Értékelés létrehozása", description = "Értékelés létrehozása")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "", required = true, content = @Content(
            mediaType = "application/json",
            schemaProperties = {
                    @SchemaProperty(name = "reviewText", schema = @Schema(implementation = String.class, description = "Az értékelés szövege", defaultValue = "0")),
                    @SchemaProperty(name = "rating", schema = @Schema(implementation = Double.class, description = "Az értékelés értéke, minimum 0 és maximum 5 lehet.", defaultValue = "0")),
                    @SchemaProperty(name = "studentId", schema = @Schema(implementation = Integer.class, description = "Az író diákhoz tartozó id.", defaultValue = "0")),
                    @SchemaProperty(name = "isAnonymous", schema = @Schema(implementation = Boolean.class, description = "Azt mutatja, hogy az író névtelenül szeretné közzé tenni a véleményt.", defaultValue = "0")),
                    @SchemaProperty(name = "instructorId", schema = @Schema(implementation = Integer.class, description = "A véleményhez tartozó oktatónak az id-ja", defaultValue = "0")),
                    @SchemaProperty(name = "schoolId", schema = @Schema(implementation = Integer.class, description = "A véleményhez tartozó iskola id-ja.", defaultValue = "0")),
            }
    ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres létrehozás", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Review.class)
            )),
            @ApiResponse(responseCode = "404", description = "", content = @Content),
            @ApiResponse(responseCode = "415", description = "", content = @Content),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghivása hiányos requestBody-val", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba", content = @Content),
    })
    @PostMapping("")
    public ResponseEntity<Object> addReview(@RequestBody JsonNode requestBody) {
    return reviewService.addReview(requestBody.get("reviewText").asText(), requestBody.get("rating").asDouble(), requestBody.get("studentId").asInt(), requestBody.get("isAnonymous").asBoolean(false) , requestBody.get("instructorId").asInt(), requestBody.get("schoolId").asInt());
    }

    @Operation(summary = "Review törlése", description = "A keresett review-t kitörli")
    @Parameter(name = "id", description = "A törlendő review-hoz tartozó id.", required = true, in = ParameterIn.PATH)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres törlés", content = @Content),
            @ApiResponse(responseCode = "404", description = "Nem létező review törlése", content = @Content),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content),
    })
    @DeleteMapping("/deleteReview/{id}")
    public ResponseEntity<Object> deleteReview(@PathVariable("id") int id) {
        return reviewService.deleteReview(id);
    }
}
