package csapat.DrivingLicenseAppAPI.controller;

import com.fasterxml.jackson.databind.JsonNode;
import csapat.DrivingLicenseAppAPI.entity.Users;
import csapat.DrivingLicenseAppAPI.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Bejelentkezés", description = "Az email és jelszó alapján visszaad egy User objectet.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Az email-t és jelszót tartalmazó object.", required = true, content = @Content(
            mediaType = "application/json",
            schemaProperties = {
                    @SchemaProperty(name = "email", schema = @Schema(implementation = String.class, description = "A felhasználó által megadott email.")),
                    @SchemaProperty(name = "password", schema = @Schema(implementation = String.class, description = "A felhasználó általt megadott jelszó."))
            }
    ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres bejelentkezés", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Users.class)
            )),
            @ApiResponse(responseCode = "404", description = "A bejelentkezés sikertelen volt és nem talált olyan User-t az adatbázisban.", content = @Content),
            @ApiResponse(responseCode = "415", description = "A felhasználó felépítésében helytelen email címet adott meg.", content = @Content),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghivása requestBody vagy rosszul felépitett requestBody-val", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content),
    })
    @PostMapping("/login")
    public ResponseEntity<JsonNode> login(@RequestBody JsonNode loginBody) {
        return userService.login(loginBody.get("email").asText(), loginBody.get("password").asText());
    }

    @Operation(summary = "Regisztráció", description = "Új profil létrehozása.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Az új felhasználó objectje. Az object id attributumának null-nak kell, hogy legyen.", content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Users.class)
    ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres regisztráció.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Nem létező education attributumnál.", content = @Content),
            @ApiResponse(responseCode = "409", description = "Már regisztrált e-maillel vagy telefonszámmal történő regisztrálás", content = @Content),
            @ApiResponse(responseCode = "415", description = "Ha a felhasználó felépítésében helytelen jelszavat, email címet vagy telefonszámot add meg.", content = @Content),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content),
    })
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody Users newUser) {
        return userService.register(newUser);
    }

    //password reset
    @Operation(summary = "Verifikációs kód megszerzése", description = "A password resetkor ez az endpoint generál egy hitelesitő kódot. Az endpoint email alapján azonosítja be a user-t.")
    @Parameters({
            @Parameter(name = "email", description = "Az az email cím amelyre a felhasználó szeretné megszerezni a hitelesitő kódót", required = true)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres regisztráció.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Nem létező education attributumnál.", content = @Content),
            @ApiResponse(responseCode = "415", description = "Ha a felhasználó felépítésében helytelen jelszavat, email címet vagy telefonszámot add meg.", content = @Content),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content),
    })
    @GetMapping("/getVerificationCode")
    public ResponseEntity<Object> getVerificationCode(@RequestParam("email") String email) {
        return userService.getVerificationCode(email);
    }

    @Operation(summary = "Verifikációs kód ellenőrzése", description = "Az adott verifikációs kód helyességét ellenőrzi. Az endpoint az adott kódot és egy email címet fog bekérni.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "", required = true, content = @Content(
            mediaType = "application/json",
            schemaProperties = {
                    @SchemaProperty(name = "vCode", schema = @Schema(implementation = String.class, description = "A user által beírt hitelesitő kód.")),
                    @SchemaProperty(name = "email", schema = @Schema(implementation = String.class, description = "Az email cím amelyre kapta a hitelesitő kódot.")),
            }
    ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Helyes hitelesitő kód megadása.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Boolean.class, name = "success", description = "Azt mutatja meg, hogy megfelelő kódot adott-e meg a felhasználó.")
            )),
            @ApiResponse(responseCode = "404", description = "Ha a user által beírt hitelesitő kód nem megfelelő", content = @Content),
            @ApiResponse(responseCode = "415", description = "Ha a felhasználó felépítésében helytelen jelszavat, email címet vagy telefonszámot add meg.", content = @Content),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content),
    })
    @PostMapping("/checkVerificationCode")
    public ResponseEntity<Object> checkVerificationCode(@RequestBody JsonNode body) {
        return userService.checkVerificationCode(body.get("vCode").asText(null), body.get("email").asText(null));
    }

    @Operation(summary = "Jelszó módosítás", description = "Jelszó módosítás")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "A módositáshoz szükséges adatokat tárolja. A fiókhoz tartozó email-t és az új jelszót.", required = true, content = @Content(
            mediaType = "application/json",
            schemaProperties = {
                    @SchemaProperty(name = "email", schema = @Schema(implementation = String.class, description = "A felhasználó által megadott e-mail cím. Az e-mail címnek szerepelnie kell az adatbázisban.")),
                    @SchemaProperty(name = "newPassword", schema = @Schema(implementation = String.class, description = "A felhasználó által megadott új jelszó. A jelszó legalább 8, legfeljebb 16 karakter hosszú lehet. Tartalmaznia kell kisbetűt, nagybetűt, számot és speciális karaktert.")),
            }
    ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres jelszó módositás", content = @Content),
            @ApiResponse(responseCode = "404", description = "A felhasználó által megadott fiók nem létezik", content = @Content),
            @ApiResponse(responseCode = "415", description = "Szerkezetileg helytelen e-mail cím vagy jelszó", content = @Content),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghivása request body nélkül", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content)
    })
    @PatchMapping("/passwordReset")
    public ResponseEntity<Object> updatePassword(@RequestBody JsonNode body) {
        return ResponseEntity.ok(userService.updatePassword(body.get("email").asText(), body.get("newPassword").asText()).getBody());
    }

    //Frissitesek:
    @Operation(summary = "Profil frissitése", description = "Az adott profilnak az adatait változtatja meg.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "A frisstet fiók User object-je.", required = true, content = @Content(
            mediaType = "application/json",
            schemaProperties = {
                    @SchemaProperty(name = "id", schema = @Schema(implementation = Integer.class, description = "")),
                    @SchemaProperty(name = "firstName", schema = @Schema(implementation = String.class, description = "")),
                    @SchemaProperty(name = "lastName", schema = @Schema(implementation = String.class, description = "")),
                    @SchemaProperty(name = "email", schema = @Schema(implementation = String.class, description = "")),
                    @SchemaProperty(name = "phone", schema = @Schema(implementation = String.class, description = "")),
                    @SchemaProperty(name = "birthDateText", schema = @Schema(implementation = String.class, description = "")),
                    @SchemaProperty(name = "gender", schema = @Schema(implementation = String.class, description = "")),
                    @SchemaProperty(name = "educationId", schema = @Schema(implementation = Integer.class, description = "")),
            }
    ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres adat(ok) frissités", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Users.class)
            )),
            @ApiResponse(responseCode = "404", description = "Olyan fiókot szeretne frissiteni, amely nem létzik vagy olyan végzettséget szeretne amely nem létezik.", content = @Content),
            @ApiResponse(responseCode = "409", description = "Már regisztrált email vagy telefonszám megadása", content = @Content),
            @ApiResponse(responseCode = "415", description = "A felhasználó felépítésében helytelen email címet, jelszót vagy telefonszámot adott meg.", content = @Content),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content),
    })
    @PutMapping("/update")
    public ResponseEntity<Object> updateUser(@RequestBody JsonNode requestBody) {
        return userService.updateUser(requestBody.get("id").asInt(), requestBody.get("firstName").asText(null), requestBody.get("lastName").asText(null), requestBody.get("email").asText(null), requestBody.get("phone").asText(null), requestBody.get("birthDateText").asText(null), requestBody.get("gender").asText(null), requestBody.get("educationId").asInt());
    }

    @Operation(summary = "Profilkép cseréje", description = "Az adott profilnak a profilképjét változtatja meg.")
    @Parameters({
            @Parameter(name = "id", description = "A felhasználóhoz tartozó id.", required = true, in = ParameterIn.PATH),
            @Parameter(name = "pfpImg", description = "A fekhasználó által kiválasztott új profilkép fájla.", required = true, in = ParameterIn.QUERY, schema = @Schema(implementation = MultipartFile.class))
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres adat(ok) frissités", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Users.class, description = "A frissitett profilképpel rendelkező fiók.")
            )),
            @ApiResponse(responseCode = "404", description = "Olyan fiókot szeretne frissiteni, amely nem létzik vagy olyan végzettséget szeretne amely nem létezik.", content = @Content),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content),
    })
    @PatchMapping("/pfp/{id}")
    public ResponseEntity<Object> updatePfp(@PathVariable("id") Integer id, @RequestParam("pfpImg") MultipartFile file) {
        return userService.updatePfp(id, file);
    }

    //Torles
    @Operation(summary = "Profilok törlése", description = "Id alapján töröl egy User-t.")
    @Parameter(name = "id", description = "A felhasználóhoz tartozó id.", required = true, in = ParameterIn.PATH)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres fiók törlés" , content = @Content),
            @ApiResponse(responseCode = "404", description = "Olyan fiókot szeretne törölni, amely nem létzik.", content = @Content),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content),
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Integer id) {
        return userService.deleteUser(id);
    }

    @Operation(summary = "User id alapján", description = "User-t id alapján returnol.")
    @Parameter(name = "id", description = "A felhasználóhoz tartozó id.", required = true, in = ParameterIn.PATH)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres fiók lekérés", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Users.class, description = "A keresett felhasználó object-je")
            )),
            @ApiResponse(responseCode = "404", description = "Olyan fiókot szeretne lekérni, amely nem létzik.", content = @Content),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable("id") Integer id) {
        return userService.getUserById(id);
    }
}
