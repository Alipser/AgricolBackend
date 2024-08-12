package agricol.backend.repositorios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import agricol.backend.entidades.Transaccion;
import agricol.backend.entidades.Usuario;

import java.util.List;


@Repository
public interface TransaccionRepositorio extends JpaRepository<Transaccion, Integer> {

    public List<Transaccion> findByUsuarioComprador(Usuario comprador);

    public List<Transaccion> findByUsuarioVendedor(Usuario vendedor);
    
}
