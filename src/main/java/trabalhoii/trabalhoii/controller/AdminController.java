package trabalhoii.trabalhoii.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trabalhoii.trabalhoii.service.AdminService;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> verDashboard() {
        return ResponseEntity.ok(adminService.gerarDashboard());
    }

    @PostMapping("/exportar")
    public ResponseEntity<String> exportarDados() {
        String resultado = adminService.exportarParaSQL();
        return ResponseEntity.ok(resultado);
    }
}