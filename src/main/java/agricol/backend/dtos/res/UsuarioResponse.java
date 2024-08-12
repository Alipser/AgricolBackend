package agricol.backend.dtos.res;


import agricol.backend.entidades.Rol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponse {
    private int idusuario;
    private String nombre;
    private String apellido;
    private String direccion;
    private String email;
    private Rol rol; //DEbo cambiar por el RolREsponse
}