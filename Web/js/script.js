const textArea = document.getElementById("text-area-principal");
const botonTokenizar = document.getElementById("boton-tokenizar");
const botonAST = document.getElementById("boton-ast");

const contenedorResultados = document.getElementById("contenedor-token-ast");
const botonPagAnt = document.getElementById("boton-ant-pag");
const botonPagSig = document.getElementById("boton-sig-pag");
let indicePagina = 0;
let totalPaginas = 0;

//BLOQUES DE CODIGO PARA MOSTRAR LA TABLA DE TOKENS

botonTokenizar.addEventListener("click", async () => {
  const yaAgregado = contenedorResultados.getElementsByClassName("div-tokens");
  if (yaAgregado.length == 0) {
    const formData = new FormData();
    formData.append("cadena", textArea.value);
    formData.append("indice", indicePagina);

    const response = await realizarPeticionPOST(
      "http://localhost:8080/compilador/listar-tokens",
      formData
    );

    if (!response.ok) {
      return;
    }

    const json = await response.json();

    if (json.errores.length > 0) {
      alert(json.errores.join("\n"));
    }

    console.log(json);

    totalPaginas = json.tokens.totalPages;

    const div = document.createElement("div");
    div.className = "div-tokens";
    const pNumPag = document.createElement("p");
    pNumPag.id = "p-num-pag";
    pNumPag.textContent = `pag ${indicePagina + 1} de ${totalPaginas}`;
    const tabla = document.createElement("table");
    tabla.id = "tabla-tokens";
    div.appendChild(pNumPag);
    div.appendChild(tabla);
    contenedorResultados.appendChild(div);
    crearTabla(tabla, pNumPag, json.tokens.content);
    accionesBotones(div);
  }
});

function accionesBotones(div) {
  const divboton = document.createElement("div");
  divboton.className = "botones-tabla-tokens";
  const btn1 = document.createElement("button");
  const btn2 = document.createElement("button");
  btn1.textContent = "Anterior";
  btn2.textContent = "Siguiente";
  btn1.id = "boton-ant-pag";
  btn1.className = "btn btn-secondary";
  btn2.id = "boton-sig-pag";
  btn2.className = "btn btn-secondary";
  divboton.appendChild(btn1);
  divboton.appendChild(btn2);
  div.appendChild(divboton);

  btn1.addEventListener("click", async () => {
    console.log("menos");
    if (indicePagina > 0) {
      indicePagina--;
      funcionBoton();
    }
  });

  btn2.addEventListener("click", async () => {
    console.log("más");
    if (indicePagina < totalPaginas - 1) {
      indicePagina++;
      funcionBoton();
    }
  });
}

async function funcionBoton() {
  const formData = new FormData();
  formData.append("cadena", textArea.value);
  formData.append("indice", indicePagina);

  const response = await realizarPeticionPOST(
    "http://localhost:8080/compilador/listar-tokens",
    formData
  );

  if (!response.ok) {
    return;
  }
  const json = await response.json();
  const tabla = document.getElementById("tabla-tokens");
  const pNumPag = document.getElementById("p-num-pag");
  crearTabla(tabla, pNumPag, json.tokens.content);

  console.log(json);
}

function crearTabla(tabla, pNumPag, tokens) {
  tabla.innerHTML = "";
  pNumPag.textContent = `pag ${indicePagina + 1} de ${totalPaginas}`;
  const thead = document.createElement("thead");
  const tbody = document.createElement("tbody");
  const filaH = document.createElement("tr");
  const celdaHLexema = document.createElement("th");
  const celdaHToken = document.createElement("th");

  celdaHLexema.textContent = "Lexema";
  celdaHToken.textContent = "Token";
  filaH.appendChild(celdaHLexema);
  filaH.appendChild(celdaHToken);
  thead.appendChild(filaH);

  tokens.forEach((token) => {
    const fila = document.createElement("tr");
    const celdaLexema = document.createElement("td");
    const celdaToken = document.createElement("td");

    celdaLexema.textContent = token.lexema;
    celdaToken.textContent = token.token;
    fila.appendChild(celdaLexema);
    fila.appendChild(celdaToken);
    tbody.appendChild(fila);
  });
  tabla.appendChild(thead);
  tabla.appendChild(tbody);
}

// BLOQUE DE CODIGO PARA MOSTRAR EL PARSER

botonAST.addEventListener("click", async () => {
  const yaAgregado = contenedorResultados.getElementsByClassName("div-ast");
  if (yaAgregado.length == 0) {
    console.log(textArea.value);
    const response = await realizarPeticionPOST(
      "http://localhost:8080/compilador/recorridos",
      textArea.value
    );

    let result = await response.json();

    generarParser(result);

    generarSemantica(result);
  }
});

function generarParser(result) {
  const div = document.createElement("div");
  div.className = "div-ast";

  if (result.errores.length > 0) {
    alert(result.errores.join("\n"));
  }

  const p = document.createElement("textarea");
  p.innerHTML = result.compilador;
  p.className = "form-control";
  // .replaceAll("\n", "<br>")
  // .replaceAll("\t", "&emsp;");
  // .replaceAll("\t", "  ");
  console.log(result);
  p.readOnly = true;

  div.appendChild(p);
  contenedorResultados.appendChild(div);
}

function generarSemantica(result) {
  const errores = result.semantico.errores;
  const mapa = result.semantico.mapa;

  const divE = document.getElementById("contenedor-tabla-semantica-e");
  const divM = document.getElementById("contenedor-tabla-semantica-r");

  if (errores.length > 0) {
    const table = document.createElement("table");
    const thead = document.createElement("thead");
    const tbody = document.createElement("tbody");
    const filaH = document.createElement("tr");
    const celdaExpresion = document.createElement("th");
    const celdaError = document.createElement("th");
    celdaExpresion.textContent = "Expresion";
    celdaError.textContent = "Error";
    filaH.appendChild(celdaExpresion);
    filaH.appendChild(celdaError);

    errores.forEach((error) => {
      const filaB = document.createElement("tr");
      const celdaEX = document.createElement("td");
      const celdaER = document.createElement("td");
      celdaEX.textContent = error.expresion;
      celdaER.textContent = error.error;
      filaB.appendChild(celdaEX);
      filaB.appendChild(celdaER);
      tbody.appendChild(filaB);
    });

    thead.appendChild(filaH);
    table.appendChild(thead);
    table.appendChild(tbody);
    divE.appendChild(table);
  }

  if (Object.keys(mapa).length > 0) {
    const table = document.createElement("table");
    const thead = document.createElement("thead");
    const tbody = document.createElement("tbody");
    const filaH = document.createElement("tr");
    const celdaTipo = document.createElement("th");
    const celdaNombre = document.createElement("th");
    const celdaValor = document.createElement("th");
    const celdaErrores = document.createElement("th");
    celdaTipo.textContent = "Tipo de dato";
    celdaNombre.textContent = "Nombre de variable";
    celdaValor.textContent = "Valor de la variable";
    celdaErrores.textContent = "Errores en la asignacion";
    for (const nombreVar in mapa) {
      const filaB = document.createElement("tr");
      const celdaT = document.createElement("td");
      const celdaN = document.createElement("td");
      const celdaV = document.createElement("td");
      const celdaE = document.createElement("td");
      celdaT.textContent = mapa[nombreVar].tipo;
      celdaN.textContent = mapa[nombreVar].nombre;
      celdaV.textContent =
        mapa[nombreVar].valor !== null
          ? mapa[nombreVar].valor
          : "El valor no pudo ser asignado";
      const errores = mapa[nombreVar].erroresSemanticos;
      if (errores.length > 0) {
        errores.forEach((error, i) => {
          celdaE.textContent =
            celdaE.textContent + (i + 1) + "." + error + "\n";
        });
      } else {
        celdaE.textContent = "Sin errores semanticos";
      }
      filaB.appendChild(celdaT);
      filaB.appendChild(celdaN);
      filaB.appendChild(celdaV);
      filaB.appendChild(celdaE);
      tbody.appendChild(filaB);
    }
    filaH.appendChild(celdaTipo);
    filaH.appendChild(celdaNombre);
    filaH.appendChild(celdaValor);
    filaH.appendChild(celdaErrores);
    thead.appendChild(filaH);
    table.appendChild(thead);
    table.appendChild(tbody);

    divM.appendChild(table);
  }
}

//BLOQUE DE CODIGO PARA REALIZAR PETICIONES Y VALIDAR ERRORES

async function realizarPeticionPOST(url, body) {
  try {
    const response = await fetch(url, {
      method: "POST",
      body,
    });

    if (!response.ok) {
      const mensaje = await response.json();
      throw new Error(mensaje.mensaje);
    }

    return response;
  } catch (error) {
    console.log("Ocurrio un error: " + error);
    alert(error);
  }
}
