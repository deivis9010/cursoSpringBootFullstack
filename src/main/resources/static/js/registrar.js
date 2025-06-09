
async function registrarUsuario() {
    const nombre = document.getElementById("nombre").value;
    const apellidos = document.getElementById("apellidos").value;
    const email = document.getElementById("email").value;
    const telefono = document.getElementById("telefono").value;
    const password = document.getElementById("exampleInputPassword").value;
    // Validación opcional: asegúrate de que los passwords coincidan
    const repetirPassword = document.getElementById("exampleRepeatPassword").value;

    if (password !== repetirPassword) {
        alert("Las contraseñas no coinciden.");
        return null;
    }

    const datos = {
        nombre: nombre,
        apellidos: apellidos,
        email: email,
        telefono: telefono,
        password: password

    };
  fetch("http://localhost:8080/addusuario",{
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(datos)
  })
    .then(respuesta => {
      if (!respuesta.ok) {
        throw new Error(`Error en la solicitud: ${respuesta.status}`);
      }
      alert("Usuario Insertado!!")
      window.location.href = "login.html";
      return respuesta;
    })

    .catch(error => {
      console.error("Error al insertar usuarios:", error);
    });
}







// Ejecutar cuando el DOM esté listo
$(document).ready(function() {
  //cargarUsuarios();
});

