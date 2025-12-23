package csapat.DrivingLicenseAppAPI.controller;

import csapat.DrivingLicenseAppAPI.entity.Review;
import csapat.DrivingLicenseAppAPI.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("")
    public ResponseEntity<Object> getReviews(@RequestParam("about") String about, @RequestParam("aboutId") Integer aboutId) {
        return reviewService.getReviews(about, aboutId);
    }

    @Operation(summary = "Review létrezohása (iskola)", description = "Review létrehozása egy iskoláról")
    @Parameter(name = "id", description = "A keresett iskolához tartozó id.", required = true, in = ParameterIn.PATH)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Az új review object-e", required = true, useParameterTypeSchema = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres review készítés"),
            @ApiResponse(responseCode = "404", description = "Vagy nem létező diák hozta létre a review-t vagy nem létező iskoláról lett készítve a review."),
            @ApiResponse(responseCode = "415", description = ""),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody"),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba."),
    })
    @PostMapping("/school/{id}")
    public ResponseEntity<Object> createSchoolReview(@RequestBody Review newReview, @PathVariable("id") Integer schoolId) {
        return reviewService.createSchoolReview(newReview, schoolId);
    }

    @Operation(summary = "Review létrezohása (oktató)", description = "Review létrehozása egy oktatóról")
    @Parameter(name = "id", description = "A keresett oktatóhoz tartozó id.", required = true, in = ParameterIn.PATH)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Az új review object-e", required = true, useParameterTypeSchema = true)
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Vagy nem létező diák hozta létre a review-t vagy nem létező oktatóról lett készítve a review."),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba."),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody"),
            @ApiResponse(responseCode = "200", description = "Sikeres review készítés")
    })
    @PostMapping("/instructor/{id}")
    public ResponseEntity<Object> createInstructorReview(@RequestBody Review newReview, @PathVariable("id") Integer instructorId) {
        return reviewService.createInstructorReview(newReview, instructorId);
    }

    @Operation(summary = "Review frissitése", description = "Review frissitése")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "", required = true, useParameterTypeSchema = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres frissités"),
            @ApiResponse(responseCode = "404", description = "Vagy nem létező review frissitése vagy nem létező diák tette a frissitést."),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody"),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba."),
    })
    @PutMapping("/updateReview")
    public ResponseEntity<Review> updateReview(@RequestBody Review updatedReview) {
        return reviewService.updateReview(updatedReview);
    }

    @Operation(summary = "Review törlése", description = "A keresett review-t kitörli")
    @Parameter(name = "id", description = "A törlendő review-hoz tartozó id.", required = true, in = ParameterIn.PATH)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres törlés"),
            @ApiResponse(responseCode = "404", description = "Nem létező review törlése"),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody"),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba."),
    })
    @DeleteMapping("/deleteReview/{id}")
    public ResponseEntity<Object> deleteReview(@PathVariable("id") int id) {
        return reviewService.deleteReview(id);
    }
}
