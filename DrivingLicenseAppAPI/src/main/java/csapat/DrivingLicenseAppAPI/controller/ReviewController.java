package csapat.DrivingLicenseAppAPI.controller;

import csapat.DrivingLicenseAppAPI.entity.Review;
import csapat.DrivingLicenseAppAPI.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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

    @Operation(summary = "", description = "")
    @Parameters({
            @Parameter(name = "about", description = "", required = true, in = ParameterIn.QUERY),
            @Parameter(name = "aboutId", description = "", required = true, in = ParameterIn.QUERY)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = ""),
            @ApiResponse(responseCode = "404", description = ""),
            @ApiResponse(responseCode = "415", description = ""),
            @ApiResponse(responseCode = "422", description = ""),
            @ApiResponse(responseCode = "500", description = ""),
    })
    @GetMapping("")
    public ResponseEntity<Object> getReviews(@RequestParam("about") String about, @RequestParam("aboutId") Integer aboutId) {
        return reviewService.getReviews(about, aboutId);
    }

    @Operation(summary = "", description = "")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "", required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = ""),
            @ApiResponse(responseCode = "404", description = ""),
            @ApiResponse(responseCode = "415", description = ""),
            @ApiResponse(responseCode = "422", description = ""),
            @ApiResponse(responseCode = "500", description = ""),
    })
    @PostMapping("")
    public ResponseEntity<Object> addReview(@RequestBody Review newReview) {
        return reviewService.addReview(newReview);
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
