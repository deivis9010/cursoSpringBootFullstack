
async function iniciarSesion() {

    const email = document.getElementById("email").value;

    const password = document.getElementById("exampleInputPassword").value;

    const datos = {
        email: email,

        password: password

    };
  fetch("http://localhost:8080/login",{
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
      return respuesta.text();
    })
    .then(respuesta => {
        if(respuesta === "OK" ){
            alert("Credenciales OK");
            window.location="usuarios.html"
        }
        else {
         alert("Credenciales Incorrectas");
        }

    })

    .catch(error => {
      console.error("Error al insertar usuarios:", error);
    });
}







// Ejecutar cuando el DOM esté listo
$(document).ready(function() {
  //cargarUsuarios();
});

