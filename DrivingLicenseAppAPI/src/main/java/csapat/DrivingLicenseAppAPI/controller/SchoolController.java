package csapat.DrivingLicenseAppAPI.controller;

import com.fasterxml.jackson.databind.JsonNode;
import csapat.DrivingLicenseAppAPI.entity.ExamRequest;
import csapat.DrivingLicenseAppAPI.entity.OpeningDetails;
import csapat.DrivingLicenseAppAPI.entity.School;
import csapat.DrivingLicenseAppAPI.entity.SchoolJoinRequest;
import csapat.DrivingLicenseAppAPI.repository.SchoolRepository;
import csapat.DrivingLicenseAppAPI.service.SchoolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/school")
@RequiredArgsConstructor
public class SchoolController {

    private final SchoolService schoolService;
    private final SchoolRepository schoolRepository;

    @Operation(summary = "Iskolához való jelentkezés", description = "Az iskolához való jelentkezési kérelem eldöntése, hogy elfogadja-e a felhasználó jelentkezését vagy nem.")
    @Parameter(name = "id", description = "Az adott csatlakozási kérelemhez tartozó id.", in = ParameterIn.PATH)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "A kérelemre adott status, hogy elfogadták (accept) vagy hogy elutasították (refuse)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres kérelem kezelés."),
            @ApiResponse(responseCode = "404", description = "Nem létező iskolához való borítókép felöltés."),
            @ApiResponse(responseCode = "415", description = ""),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody"),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.")
    })
    @PostMapping("/request/{id}")
    public ResponseEntity<Object> handleJoinRequest(@PathVariable("id") Integer joinRequestId, @RequestBody JsonNode requestBody) {
        return schoolService.handleJoinRequest(joinRequestId, requestBody.get("status").asText());
    }

    @Operation(summary = "Iskola frissitése", description = "Az iskola adatainak a frissitése. Minden adat frissitése kivéve a boritókép és a nyitvatartás.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "A frissitett iskolának az object-je")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres frissités."),
            @ApiResponse(responseCode = "404", description = "Nem létező iskolát szeretett volna a felhasználó frissiteni."),
            @ApiResponse(responseCode = "409", description = ""),
            @ApiResponse(responseCode = "415", description = "Felépitésben nem megfelelő email cím vagy telefonszám."),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody"),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba."),
    })
    @PutMapping("")
    public ResponseEntity<Object> updateSchool(@RequestBody School updatedSchool) {
        return schoolService.updateSchool(updatedSchool);
    }

    @Operation(summary = "Iskola boritókép csere.")
    @PatchMapping("/coverImg/{id}")
    @Parameters({
            @Parameter(name = "id", description = "Az adott fiókhoz tartozó id.", in = ParameterIn.PATH),
            @Parameter(name = "bannerImg", description = "A feltöltött boritókép.")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres boritókép csere."),
            @ApiResponse(responseCode = "404", description = "Nem létező iskolához való borítókép felöltés."),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody"),
            @ApiResponse(responseCode = "500", description = "A fájl-lal való műveletek során hiba keletkezett/A server okozta hiba.")
    })
    public ResponseEntity<Object> changeCoverImg(@PathVariable("id") Integer id, @RequestParam("bannerImg") MultipartFile coverImg) {
        return schoolService.changeCoverImg(id, coverImg);
    }

    @Operation(summary = "Nyitvatartás hozzáadása.")
    @Parameter(name = "id", description = "Az adott fiókhoz tartozó id.", in = ParameterIn.PATH)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Az új nyitvatartásnak az objectje")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres nyitvatartás hozzáadás."),
            @ApiResponse(responseCode = "404", description = "Nem létező iskolához való nyitvatartás hozzáadás."),
            @ApiResponse(responseCode = "415", description = "Érvénytelen nyitás és zárás óra kombináció."),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody"),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba."),
    })
    @PostMapping("/{id}/openingDetails")
    public ResponseEntity<Object> addOpeningDetails(@PathVariable("id") Integer id, @RequestBody OpeningDetails newOpeningDetails) {
        return schoolService.addOpeningDetails(id, newOpeningDetails);
    }

    @Operation(summary = "Nyitvatartás modósitása.")
    @Parameter(name = "id", description = "Az adott fiókhoz tartozó id.", in = ParameterIn.PATH)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "A frissitett nyitvatartásnak az objectje")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres nyitvatartás frissités."),
            @ApiResponse(responseCode = "404", description = "Nem létező iskolához való nyitvatartás frissités."),
            @ApiResponse(responseCode = "415", description = "Érvénytelen nyitás és zárás óra kombináció."),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody"),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba."),
    })
    @PutMapping("/{id}/openingDetails")
    public ResponseEntity<Object> updateOpeningDetails(@PathVariable("id") Integer id, @RequestBody OpeningDetails updatedOpeningDetails) {
        return schoolService.updateOpeningDetails(id, updatedOpeningDetails);
    }

    @Operation(summary = "Iskalához tartozó csatlakozási kérelmek", description = "Az adott iskolához tartozó csatlakozás kérelmek lekérdezése")
    @Parameter(name = "id", description = "Az adott iskolához tartozó id.", required = true, in = ParameterIn.PATH)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres kérelem küldés"),
            @ApiResponse(responseCode = "404", description = "Egy nem létező iskolához tartozó kérelmek lekérdezése"),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody"),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.")
    })
    @GetMapping("/{id}/joinRequests")
    private ResponseEntity<List<SchoolJoinRequest>> getAllJoinRequest(@PathVariable("id") Integer id) {
        return schoolService.getAllJoinRequest(id);
    }

    @Operation(summary = "Iskalához tartozó vizsga kérelmek", description = "Az adott iskolához tartozó vizsga kérelmek lekérdezése")
    @Parameter(name = "id", description = "Az adott iskolához tartozó id.", required = true, in = ParameterIn.PATH)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres kérelem küldés"),
            @ApiResponse(responseCode = "404", description = "Egy nem létező iskolához tartozó kérelmek lekérdezése"),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody"),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba."),
    })
    @GetMapping("/{id}/examRequest")
    private ResponseEntity<List<ExamRequest>> getAllExamRequest(@PathVariable("id") Integer id) {
        return schoolService.getAllExamRequest(id);
    }

    @Operation(summary = "Iskola törlése", description = "Az adott iskola törlése id alapján.")
    @Parameter(name = "id", description = "Az adott iskolához tartozó id.", required = true, in = ParameterIn.PATH)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres iskola törlés"),
            @ApiResponse(responseCode = "404", description = "Egy nem létező iskola törlése"),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody"),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba."),
    })
    @DeleteMapping("/{id}")
    private ResponseEntity<Object> deleteSchool(@PathVariable("id") Integer id) {
        return schoolService.deleteSchool(id);
    }

    //Kereso
    @Operation(summary = "Iskolák keresése", description = "Iskola keresése város és név alapján")
    @Parameters({
            @Parameter(name = "name", description = "A keresett iskola neve.", in = ParameterIn.QUERY),
            @Parameter(name = "town", description = "A keresett iskolához tartozó város.", in = ParameterIn.QUERY),
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres lekérdezés."),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba."),
    })
    @GetMapping("")
    private ResponseEntity<List<School>> getSchoolsBySearch(@RequestParam(value = "name", defaultValue = "") String name, @RequestParam(value = "town", defaultValue = "Budapest") String town) {
        return schoolService.getSchoolBySearch(name, town);
    }

    @Operation(summary = "Iskola id alapján", description = "Iskola lekérdezése id alapján.")
    @Parameter(name = "id", description = "Az iskolához tartozó id.", required = true, in = ParameterIn.PATH)
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Egy nem létező iskola lekérdezése"),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody"),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba."),
            @ApiResponse(responseCode = "200", description = "Sikeres kérelem küldés")
    })
    @GetMapping("/{id}")
    private ResponseEntity<School> getSchoolById(@PathVariable("id") Integer id) {
        return schoolService.getSchoolById(id);
    }
}
