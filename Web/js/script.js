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
      "http://localhost:8080/listar-tokens",
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
    "http://localhost:8080/listar-tokens",
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
    const response = await realizarPeticionPOST(
      "http://localhost:8080/recorridos",
      textArea.value
    );

    const div = document.createElement("div");
    div.className = "div-ast";
    let result = await response.json();

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
});

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
