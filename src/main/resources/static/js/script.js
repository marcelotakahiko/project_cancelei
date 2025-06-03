document.addEventListener("DOMContentLoaded", function () {
    const botoesExcluir = document.querySelectorAll(".btn-delete");

    botoesExcluir.forEach(function (botao) {
        botao.addEventListener("click", function (event) {
            const confirmacao = confirm("Tem certeza que deseja excluir este registro?");
            if (!confirmacao) {
                event.preventDefault();
            }
        });
    });
});
