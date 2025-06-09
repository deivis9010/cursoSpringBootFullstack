
function cargarUsuarios() {
  fetch("http://localhost:8080/getlistusuarios",getHeader())
    .then(respuesta => {
      if (!respuesta.ok) {
        throw new Error(`Error en la solicitud: ${respuesta.status}`);
      }
      return respuesta.json();
    })
    .then(usuarios => {
      // Limpiar DataTable si ya está inicializada
      if ($.fn.DataTable.isDataTable('#usuarios')) {
        $('#usuarios').DataTable().clear().destroy();
      }

      // Limpiar el tbody antes de agregar nuevos datos
      const tbody = document.querySelector("#usuarios tbody");
      tbody.innerHTML = "";

      // Agregar filas dinámicamente
      usuarios.forEach(usuario => {
        const fila = document.createElement("tr");

        fila.innerHTML = `
          <td>${usuario.id}</td>
          <td>${usuario.nombre} ${usuario.apellidos}</td>
          <td>${usuario.email}</td>
          <td>${usuario.telefono}</td>
          <td>
            <a href="#" title="Editar" class="btn btn-danger btn-circle">
              <i class="fas fa-edit"></i>
            </a>
            <a href="#" title="Eliminar" class="btn btn-danger btn-circle" onClick="eliminarUsuario(${usuario.id})">
              <i class="fas fa-trash"></i>
            </a>
          </td>
        `;

        tbody.appendChild(fila);
      });

      // Re-inicializar DataTable
      $('#usuarios').DataTable({
        responsive: true,
        language: {
          url: "//cdn.datatables.net/plug-ins/1.13.5/i18n/es-ES.json"
        }
      });
    })
    .catch(error => {
      console.error("Error al cargar los usuarios:", error);
    });
}


async  function eliminarUsuario(id) {

  if (confirm('Desea elimimar?')){
    fetch("http://localhost:8080/delusuario/"+id, getHeaderE())
         .then(respuesta => {
           if (!respuesta.ok) {
             throw new Error(`Error en la solicitud: ${respuesta.status}`);
           }
           cargarUsuarios();
         })


         .catch(error => {
           console.error("Error al eliminar usuario:", error);
         });
  }

}


function getHeader(){
 return {method: "GET",
         headers: {
          "Content-Type": "application/json",
          "Authorization": localStorage.token
            },


                                                            };
}

function getHeaderE(){
 return {
          method: "DELETE",
          headers: {
            "Content-Type": "application/json",
            "Authorization": localStorage.token
          },
           Authorization: localStorage.token

                                                            };
}
function actualizarEmailUsuario() {
    const txtUsername=document.getElementById('txt-username');

    txtUsername.outerHTML = localStorage.email;

}
// Ejecutar cuando el DOM esté listo
$(document).ready(function() {
  cargarUsuarios();
  actualizarEmailUsuario();
});

