package csapat.DrivingLicenseAppAPI.controller;

import csapat.DrivingLicenseAppAPI.entity.School;
import csapat.DrivingLicenseAppAPI.entity.SchoolJoinRequest;
import csapat.DrivingLicenseAppAPI.entity.Students;
import csapat.DrivingLicenseAppAPI.repository.SchoolRepository;
import csapat.DrivingLicenseAppAPI.service.SchoolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/school")
@RequiredArgsConstructor
public class SchoolController {

    private final SchoolService schoolService;
    private final SchoolRepository schoolRepository;

    @Operation(summary = "Iskolához való jelentkezés", description = "Az iskolához való jelentkezési kérelem eldöntése, hogy elfogadja-e a felhasználó jelentkezését vagy nem.")
    @PostMapping("/request/{id}")
    public ResponseEntity<Object> handleJoinRequest(@PathVariable("id") Integer joinRequestId, @RequestBody Map<String, String> requestBody){
        return schoolService.handleJoinRequest(joinRequestId, requestBody.get("status"));
    }

    @Operation(summary = "Iskola frissitése", description = "Az iskola adatainak a frissitése. Minden adat frissitése kivéve a boritókép és a nyitvatartás.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "A frissitett iskolának az object-je")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres frissités."),
            @ApiResponse(responseCode = "404", description = "Nem létező iskolát szeretett volna a felhasználó frissiteni."),
            @ApiResponse(responseCode = "417", description = "Felépitésben nem megfelelő email cím vagy telefonszám.")
    })
    @PutMapping("")
    public ResponseEntity<School> updateSchool(@RequestBody School updatedSchool){
        return schoolService.updateSchool(updatedSchool);
    }

    @Operation(summary = "Iskola boritókép csere.")
    @PatchMapping("/coverImg/{id}")
    public ResponseEntity<School> changeCoverImg(){
        return schoolService.changeCoverImg();
    }

    @Operation(summary = "Nyitvatartás hozzáadása.")
    @PostMapping("/{id}/openingDetails")
    public ResponseEntity<School> addOpeningDetails(){
        return schoolService.addOpeningDetails();
    }

    @Operation(summary = "Nyitvatartás modósitása.")
    @PutMapping("/{id}/openingDetails")
    public ResponseEntity<School> updateOpeningDetails(){
        return schoolService.updateOpeningDetails();
    }

    @Operation(summary = "Iskalához tartozó csatlakozási kérelmek", description = "Az adott iskolához tartozó csatlakozás kérelmek lekérdezése")
    @Parameter(name = "id", description = "Az adott iskolához tartozó id.", required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Egy nem létező iskolához tartozó kérelmek lekérdezése"),
            @ApiResponse(responseCode = "200", description = "Sikeres kérelem küldés"),
    })
    @GetMapping("/{id}/joinRequests")
    private ResponseEntity<List<SchoolJoinRequest>> getAllJoinRequestBySchool(@PathVariable("id") Integer id){
        return schoolService.getAllJoinRequestBySchool(id);
    }

    //
    @GetMapping("{id}")
    public School getSchoolById(@PathVariable("id") Integer id){
        return schoolRepository.findById(id).get();
    }
}
