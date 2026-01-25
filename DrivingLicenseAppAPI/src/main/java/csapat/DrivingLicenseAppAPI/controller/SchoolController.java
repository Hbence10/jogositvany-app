package csapat.DrivingLicenseAppAPI.controller;

import com.fasterxml.jackson.databind.JsonNode;
import csapat.DrivingLicenseAppAPI.entity.OpeningDetails;
import csapat.DrivingLicenseAppAPI.entity.School;
import csapat.DrivingLicenseAppAPI.entity.SchoolJoinRequest;
import csapat.DrivingLicenseAppAPI.service.SchoolService;
import csapat.DrivingLicenseAppAPI.service.other.ProfileCard;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/school")
@RequiredArgsConstructor
public class SchoolController {

    private final SchoolService schoolService;

    @Operation(summary = "Iskolához való jelentkezés eldöntése", description = "Az iskolához való jelentkezési kérelem eldöntése, hogy elfogadja-e a felhasználó jelentkezését vagy nem.")
    @Parameter(name = "id", description = "Az adott csatlakozási kérelemhez tartozó id.", in = ParameterIn.PATH)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "A kérelemre adott status, hogy elfogadták (accept) vagy hogy elutasították (refuse)", required = true, content = @Content(
            mediaType = "application/json",
            schemaProperties = {
                    @SchemaProperty(name = "status", schema = @Schema(implementation = String.class, description = "Csak accept vagy refuse lehet"))
            }
    ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres kérelem kezelés.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Nem létező iskolához való borítókép felöltés.", content = @Content),
            @ApiResponse(responseCode = "415", description = "Nem létező status megadása", content = @Content),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content)
    })
    @PostMapping("/joinRequest/{id}")
    public ResponseEntity<Object> handleJoinRequest(@PathVariable("id") Integer joinRequestId, @RequestBody JsonNode requestBody) {
        return schoolService.handleJoinRequest(joinRequestId, requestBody.get("status").asText());
    }

    @Operation(summary = "Iskola frissitése", description = "Az iskola adatainak a frissitése. Minden adat frissitése kivéve a boritókép és a nyitvatartás.")
    @Parameter(name = "id", description = "Az iskolához tartozó id.", in = ParameterIn.PATH, required = true)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "A frissitett iskolának az object-je", required = true, content = @Content(
            mediaType = "application/json",
            schemaProperties = {
                    @SchemaProperty(name = "name", schema = @Schema(implementation = String.class, description = "Az iskola neve")),
                    @SchemaProperty(name = "email", schema = @Schema(implementation = String.class, description = "Az iskola e-mail cime")),
                    @SchemaProperty(name = "phone", schema = @Schema(implementation = String.class, description = "Az iskola telefonszáma")),
                    @SchemaProperty(name = "country", schema = @Schema(implementation = String.class, description = "Az a vármegye amelyben az iskola tartozkodik")),
                    @SchemaProperty(name = "town", schema = @Schema(implementation = String.class, description = "Az a város amelyben az iskola tartozkodik")),
                    @SchemaProperty(name = "address", schema = @Schema(implementation = String.class, description = "Az iskola cime")),
                    @SchemaProperty(name = "promoText", schema = @Schema(implementation = String.class, description = "Az iskola bemutatkozó szövege")),
            }
    ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres frissités.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = School.class, description = "A frissitett iskola object")
            )),
            @ApiResponse(responseCode = "404", description = "Nem létező iskolát szeretett volna a felhasználó frissiteni.", content = @Content),
            @ApiResponse(responseCode = "409", description = "Már regisztrált email vagy telefonszám megadása", content = @Content),
            @ApiResponse(responseCode = "415", description = "Felépitésben nem megfelelő email cím vagy telefonszám.", content = @Content),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content),
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateSchool(@RequestBody JsonNode requestBody, @PathVariable("id") Integer schoolId) {
        return schoolService.updateSchool(schoolId, requestBody.get("name").asText(null), requestBody.get("email").asText(null), requestBody.get("phone").asText(null), requestBody.get("country").asText(null), requestBody.get("town").asText(null), requestBody.get("address").asText(null), requestBody.get("promoText").asText(null));
    }

    @Operation(summary = "Iskola boritókép csere.")
    @PatchMapping("/coverImg/{id}")
    @Parameters({
            @Parameter(name = "id", description = "Az adott fiókhoz tartozó id.", in = ParameterIn.PATH),
            @Parameter(name = "image", description = "A feltöltött boritókép.")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres boritókép csere.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = School.class, description = "A frissitett bórítóképpel rendelkezett iskola object-je.")
            )),
            @ApiResponse(responseCode = "404", description = "Nem létező iskolához való borítókép felöltés.", content = @Content),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody", content = @Content),
            @ApiResponse(responseCode = "500", description = "A fájl-lal való műveletek során hiba keletkezett/A server okozta hiba.", content = @Content)
    })
    public ResponseEntity<Object> changeCoverImg(@PathVariable("id") Integer id, @RequestParam("image") MultipartFile coverImg) {
        return schoolService.changeCoverImg(id, coverImg);
    }

    @Operation(summary = "Nyitvatartás modósitása.")
    @Parameter(name = "id", description = "Az adott iskolához tartozó id.", in = ParameterIn.PATH)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Az iskolához tartozó frissitetett nyitvatartási lista", required = true, content = @Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = OpeningDetails.class))
    ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres nyitvatartás frissités.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = School.class, description = "A frissitett nyitvatartást tartalmazó iskola object.")
            )),
            @ApiResponse(responseCode = "404", description = "Nem létező iskolához való nyitvatartás frissités.", content = @Content),
            @ApiResponse(responseCode = "415", description = "Érvénytelen nyitás és zárás óra kombináció.", content = @Content),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content),
    })
    @PatchMapping("/{id}/openingDetails")
    public ResponseEntity<Object> updateOpeningDetails(@PathVariable("id") Integer id, @RequestBody List<OpeningDetails> updatedOpeningDetails) {
        return schoolService.updateOpeningDetails(id, updatedOpeningDetails);
    }

    @Operation(summary = "Iskalához tartozó csatlakozási kérelmek lekérdezése", description = "Az adott iskolához tartozó csatlakozás kérelmek lekérdezése")
    @Parameter(name = "id", description = "Az adott iskolához tartozó id.", required = true, in = ParameterIn.PATH)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres kérelemek lekérdezése", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = SchoolJoinRequest.class))
            )),
            @ApiResponse(responseCode = "404", description = "Egy nem létező iskolához tartozó kérelmek lekérdezése", content = @Content),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content)
    })
    @GetMapping("/{id}/joinRequests")
    private ResponseEntity<List<SchoolJoinRequest>> getAllJoinRequest(@PathVariable("id") Integer id) {
        return schoolService.getAllJoinRequest(id);
    }

    @Operation(summary = "Iskola törlése", description = "Az adott iskola törlése id alapján.")
    @Parameter(name = "id", description = "Az adott iskolához tartozó id.", required = true, in = ParameterIn.PATH)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres iskola törlés", content = @Content),
            @ApiResponse(responseCode = "404", description = "Egy nem létező iskola törlése", content = @Content),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content),
    })
    @DeleteMapping("/{id}")
    private ResponseEntity<Object> deleteSchool(@PathVariable("id") Integer id) {
        return schoolService.deleteSchool(id);
    }

    //Kereso
    @Operation(summary = "Iskolák keresése", description = "Iskola keresése város alapján")
    @Parameter(name = "town", description = "A keresett iskolához tartozó város.", in = ParameterIn.QUERY)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres lekérdezés.", content = @Content(
                    mediaType = "application/json"
            )),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content),
    })
    @GetMapping("/search")
    private ResponseEntity<List<JsonNode>> getSchoolsBySearch(@RequestParam(value = "town", defaultValue = "Budapest") String town) {
        return schoolService.getSchoolBySearch(town);
    }

    @Operation(summary = "Iskola id alapján", description = "Iskola lekérdezése id alapján.")
    @Parameter(name = "id", description = "Az iskolához tartozó id.", required = true, in = ParameterIn.PATH)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres lekérdezés", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = School.class, description = "A keresett iskola.")
            )),
            @ApiResponse(responseCode = "404", description = "Egy nem létező iskola lekérdezése", content = @Content),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content)
    })
    @GetMapping("/{id}")
    private ResponseEntity<School> getSchoolById(@PathVariable("id") Integer id) {
        return schoolService.getSchoolById(id);
    }

    @Operation(summary = "Iskola létrehozása", description = "")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "A létrehozandó iskola objectje", required = true, content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = School.class, description = "Az id-nak null-nak kell hogy legyen")
    ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres iskola létrehozás", content = @Content),
            @ApiResponse(responseCode = "422", description = "Endpoint meghívása requestBody nélkul", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content)
    })
    @PostMapping("")
    private ResponseEntity<Object> createSchool(@RequestBody School addedSchool) {
        return schoolService.createSchool(addedSchool);
    }

    @Operation(summary = "Iskola tagjainak lekérdezése", description = "Az adott iskolához tartozó tagok lekérdezése")
    @Parameters({
            @Parameter(name = "schoolId", description = "Az iskolához tartozó id.", in = ParameterIn.QUERY, required = true),
            @Parameter(name = "role", description = "A lekérdezni való tagok beosztása. Csak student vagy instructor értéke lehet.", in = ParameterIn.QUERY, required = true),
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres lekérdezés", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ProfileCard.class))
            )),
            @ApiResponse(responseCode = "404", description = "Nem létező iskola megadása", content = @Content),
            @ApiResponse(responseCode = "422", description = "Endpoint meghívása parameter nélkul", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content)
    })
    @GetMapping("/users")
    private ResponseEntity<Object> getMembersOfSchool(@RequestParam("schoolId") Integer id, @RequestParam("role") String role, Pageable pageable) {
        return schoolService.getMembersOfSchool(id, role, pageable);
    }

    @Operation(summary = "Oktató kirugása", description = "Az iskolábol az adott oktató kirugása")
    @Parameter(name = "instructorId", description = "Az adott oktatóhoz tartozó id", required = true, in = ParameterIn.QUERY)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres kirugás", content = @Content),
            @ApiResponse(responseCode = "404", description = "Nem létező oktatóhoz tartozó id.", content = @Content),
            @ApiResponse(responseCode = "422", description = "Endpoint meghivása parameter nélkül.", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content),
    })
    @DeleteMapping("/kickout")
    private ResponseEntity<Object> kickOutMember(@RequestParam("instructorId") Integer instructorId) {
        return schoolService.kickoutInstructor(instructorId);
    }

    @Operation(summary = "Az összes iskola lekérdezése", description = "Az összes iskola lekérdezése röviditett formában")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres lekérdezés", content = @Content(
                    mediaType = "application/json"
            )),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba", content = @Content)
    })
    @GetMapping("")
    private ResponseEntity<Object> getAllSchool(Pageable pageable) {
        return schoolService.getAllSchool(pageable);
    }
}
