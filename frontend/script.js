let solicitudes = [];
let idActual = 1;
let indiceEditando = null;

const formulario = document.getElementById("formSolicitud");
const tabla = document.getElementById("tablaSolicitudes");

formulario.addEventListener("submit", function (event) {
    event.preventDefault();

    const nombre = document.getElementById("nombre").value.trim();
    const pasaporte = document.getElementById("pasaporte").value.trim();
    const tipoVisa = document.getElementById("tipoVisa").value;
    const fecha = document.getElementById("fecha").value;
    const estado = document.getElementById("estado").value;

    if (nombre.length < 3) {
        alert("El nombre debe contener mínimo 3 caracteres.");
        return;
    }

    if (pasaporte.length < 5) {
        alert("El número de pasaporte debe contener mínimo 5 caracteres.");
        return;
    }

    if (tipoVisa === "") {
        alert("Seleccione un tipo de visa.");
        return;
    }

    if (fecha === "") {
        alert("Seleccione una fecha de solicitud.");
        return;
    }

    if (indiceEditando === null) {
        const solicitud = {
            id: idActual,
            nombre: nombre,
            pasaporte: pasaporte,
            tipoVisa: tipoVisa,
            fecha: fecha,
            estado: estado
        };

        solicitudes.push(solicitud);
        idActual++;

        alert("Solicitud registrada correctamente.");
    } else {
        solicitudes[indiceEditando].nombre = nombre;
        solicitudes[indiceEditando].pasaporte = pasaporte;
        solicitudes[indiceEditando].tipoVisa = tipoVisa;
        solicitudes[indiceEditando].fecha = fecha;
        solicitudes[indiceEditando].estado = estado;

        indiceEditando = null;

        document.querySelector(
            "#formSolicitud button[type='submit']"
        ).textContent = "Registrar solicitud";

        alert("Solicitud actualizada correctamente.");
    }

    formulario.reset();
    mostrarSolicitudes();
});

function mostrarSolicitudes() {
    tabla.innerHTML = "";

    solicitudes.forEach(function (solicitud, indice) {
        const fila = document.createElement("tr");

        fila.innerHTML = `
            <td>${solicitud.id}</td>
            <td>${solicitud.nombre}</td>
            <td>${solicitud.pasaporte}</td>
            <td>${solicitud.tipoVisa}</td>
            <td>${solicitud.fecha}</td>
            <td>${solicitud.estado}</td>
            <td>
                <button onclick="editarSolicitud(${indice})">
                    Editar
                </button>

                <button onclick="eliminarSolicitud(${indice})">
                    Eliminar
                </button>
            </td>
        `;

        tabla.appendChild(fila);
    });
}

function editarSolicitud(indice) {
    const solicitud = solicitudes[indice];

    document.getElementById("nombre").value = solicitud.nombre;
    document.getElementById("pasaporte").value = solicitud.pasaporte;
    document.getElementById("tipoVisa").value = solicitud.tipoVisa;
    document.getElementById("fecha").value = solicitud.fecha;
    document.getElementById("estado").value = solicitud.estado;

    indiceEditando = indice;

    document.querySelector(
        "#formSolicitud button[type='submit']"
    ).textContent = "Actualizar solicitud";

    window.scrollTo({
        top: 0,
        behavior: "smooth"
    });
}

function eliminarSolicitud(indice) {
    const confirmar = confirm(
        "¿Está seguro de eliminar esta solicitud?"
    );

    if (confirmar) {
        solicitudes.splice(indice, 1);
        mostrarSolicitudes();

        alert("Solicitud eliminada correctamente.");
    }
}