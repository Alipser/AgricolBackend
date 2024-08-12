package agricol.backend.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import agricol.backend.dtos.req.CompraRequest;
import agricol.backend.dtos.res.CompraResponse;
import agricol.backend.dtos.res.TransaccionResponse;
import agricol.backend.servicios.Inetrfaz.ICompraServicio;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/compra")
@AllArgsConstructor
public class CompraControlador {

    @Autowired
    private final ICompraServicio compraService;

    @PostMapping("/generar")
    public ResponseEntity<CompraResponse> inicioSeSion(@RequestParam String compradorId,
            @RequestBody CompraRequest compraRequest) {
        return ResponseEntity.ok(compraService.abrirTransaccion(compradorId, compraRequest));
    }

    @GetMapping("/misCompras/{compradorId}")
    public ResponseEntity<List<TransaccionResponse>> getMethodName(@PathVariable String compradorId) {
        return ResponseEntity.ok(compraService.misCompras(compradorId));
    }
    
}