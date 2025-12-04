package csapat.DrivingLicenseAppAPI.controller;

import com.fasterxml.jackson.databind.JsonNode;
import csapat.DrivingLicenseAppAPI.entity.Users;
import csapat.DrivingLicenseAppAPI.service.UserService;
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

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Bejelentkezés", description = "Az email és jelszó alapján visszaad egy User objectet.")
    @Parameters({
            @Parameter(name = "email", description = "A felhasználó által beírt email cím.", required = true),
            @Parameter(name = "password", description = "A fekhasználó által beírt jelszó.", required = true)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "A bejelentkezés sikertelen volt és nem talált olyan User-t az adatbázisban."),
            @ApiResponse(responseCode = "417", description = "A felhasználó felépítésében helytelen email címet adott meg."),
            @ApiResponse(responseCode = "200", description = "Sikeres bejelentkezés", useReturnTypeSchema = true)
    })
    @PostMapping("/login")
    public ResponseEntity<Users> login(@RequestBody JsonNode loginBody) {
        return userService.login(loginBody.get("email").asText(), loginBody.get("password").asText());
    }

    @Operation(summary = "Regisztráció", description = "Új profil létrehozása.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Az új felhasználó objectje. Az object id attributumának null-nak kell, hogy legyen.", useParameterTypeSchema = true)
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Nem létező education attributumnál."),
            @ApiResponse(responseCode = "417", description = "Ha a felhasználó felépítésében helytelen jelszavat, email címet vagy telefonszámot add meg."),
            @ApiResponse(responseCode = "200", description = "Sikeres regisztráció.", useReturnTypeSchema = true)
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
            @ApiResponse(responseCode = "404", description = "Nem létező education attributumnál."),
            @ApiResponse(responseCode = "417", description = "Ha a felhasználó felépítésében helytelen jelszavat, email címet vagy telefonszámot add meg."),
            @ApiResponse(responseCode = "200", description = "Sikeres regisztráció.", useReturnTypeSchema = true)
    })
    @GetMapping("/getVerificationCode")
    public ResponseEntity<HashMap<String, Object>> getVerificationCode(@RequestParam("email") String email) {
        HashMap<String, Object> returnObject = new HashMap<>();
        returnObject.put("isSent", userService.getVerificationCode(email).getBody());
        return ResponseEntity.ok(returnObject);
    }

    @Operation(summary = "Verifikációs kód ellenőrzése", description = "Az adott verifikációs kód helyességét ellenőrzi. Az endpoint az adott kódot és egy email címet fog bekérni.")
    @Parameters({
            @Parameter(name = "vCode", description = "A user által beírt hitelesitő kód.", required = true),
            @Parameter(name = "email", description = "Az az email cím amelyre a felhasználó szeretné megszerezni a hitelesitő kódót", required = true)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Ha a user által beírt hitelesitő kód nem megfelelő", useReturnTypeSchema = false),
            @ApiResponse(responseCode = "417", description = "Ha a felhasználó felépítésében helytelen jelszavat, email címet vagy telefonszámot add meg."),
            @ApiResponse(responseCode = "200", description = "Helyes hitelesitő kód megadása.", useReturnTypeSchema = true)
    })
    @PostMapping("/checkVerificationCode")
    public ResponseEntity<Object> checkVerificationCode(@RequestBody JsonNode body) {
        return userService.checkVCode(body.get("vCode").asText(), body.get("email").asText());
    }

    @Operation(summary = "Jelszó megváltoztatása", description = "Az adott felhasználónak a jelszavát változtatja meg.")
    @Parameters({
            @Parameter(name = "email", description = "A felhasználó által beírt email cím.", required = true),
            @Parameter(name = "newPassword", description = "A fekhasználó által beírt új jelszó.", required = true)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Olyan email címet adott meg a felhasználó, amelyhez nem tartozik egy fiók se.", useReturnTypeSchema = false),
            @ApiResponse(responseCode = "417", description = "A felhasználó felépítésében helytelen email címet vagy jelszót adott meg."),
            @ApiResponse(responseCode = "200", description = "Sikeres jelszó változtatás", useReturnTypeSchema = true)
    })
    @PatchMapping("/passwordReset")
    public ResponseEntity<String> updatePassword(@RequestBody JsonNode body) {
        return ResponseEntity.ok(userService.updatePassword(body.get("email").asText(), body.get("newPassword").asText()).getBody());
    }

    //Frissitesek:
    @Operation(summary = "Profil frissitése", description = "Az adott profilnak az adatait változtatja meg.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "A frisstet fiók User object-je.", required = true, useParameterTypeSchema = true)
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Olyan fiókot szeretne frissiteni, amely nem létzik vagy olyan végzettséget szeretne amely nem létezik.", useReturnTypeSchema = false),
            @ApiResponse(responseCode = "417", description = "A felhasználó felépítésében helytelen email címet, jelszót vagy telefonszámot adott meg."),
            @ApiResponse(responseCode = "200", description = "Sikeres adat(ok) frissités", useReturnTypeSchema = true)
    })
    @PutMapping("/update")
    public ResponseEntity<Object> updateUser(@RequestBody Users updatedUser) {
        return userService.updateUser(updatedUser);
    }

    @Operation(summary = "Profilkép cseréje", description = "Az adott profilnak a profilképjét változtatja meg.")
    @Parameters({
            @Parameter(name = "id", description = "A felhasználóhoz tartozó id.", required = true, in = ParameterIn.PATH),
            @Parameter(name = "pfpImg", description = "A fekhasználó által kiválasztott új profilkép fájla.", required = true)
    })
    @PatchMapping("/pfp/{id}")
    public ResponseEntity<Users> updatePfp(@PathVariable("id") Integer id, @RequestParam("pfpImg") MultipartFile file) {
        return userService.updatePfp(id, file);
    }

    //Torles
    @Operation(summary = "Profilok törlése", description = "Id alapján töröl egy User-t.")
    @Parameter(name = "id", description = "A felhasználóhoz tartozó id.", required = true, in = ParameterIn.PATH)
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Olyan fiókot szeretne törölni, amely nem létzik.", useReturnTypeSchema = false),
            @ApiResponse(responseCode = "200", description = "Sikeres fiók törlés", useReturnTypeSchema = true)
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Integer id) {
        return userService.deleteUser(id);
    }

    //Egyeb endpointok:
    @Operation(summary = "User id alapján", description = "User-t id alapján returnol.")
    @Parameter(name = "id", description = "A felhasználóhoz tartozó id.", required = true, in = ParameterIn.PATH)
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Olyan fiókot szeretne lekérni, amely nem létzik.", useReturnTypeSchema = false),
            @ApiResponse(responseCode = "200", description = "Sikeres fiók lekérés", useReturnTypeSchema = true)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable("id") Integer id) {
        return null;
    }
}
