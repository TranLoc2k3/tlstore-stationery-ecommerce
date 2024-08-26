function calcTotalPrice() {
    var total = 0;
    $('.total-price-item').each(function() {
        let price = $(this).text().replace(/,/g, '');
        total += parseInt(price.toLocaleString());
    });

    $('#total-price').text(total.toLocaleString());
}

$(document).ready(function() {
    calcTotalPrice();
    $('.decrement').click(function(e) {
        var productId = this.id.split('-')[1];
        var quantityInput = parseInt($('#quantity' + productId).val()) - 1;
        if (0 <= quantityInput && quantityInput <= parseInt($('#quantityAvaiable').text().replace(/,/g, ''))) {
            $.ajax({
                url: "/update-quantity",
                type: "get",
                data: {
                    productId: productId,
                    quantity: quantityInput
                },
                success: function(response){
                    if (quantityInput == 0) {
                        location.reload();
                    }
                    else {
                        $('#quantity' + productId).val(quantityInput);
                        $("#total-price-item" + productId).text(quantityInput * parseInt($("#price" + productId).text().replace(/,/g, '')));
                    }
                    calcTotalPrice();
                },
                error: function(xhr){
                    alert("An error occurred");
                }
            });
        }
    });

    $('.increment').click(function() {
        var productId = this.id.split('-')[1];
        var quantityInput = parseInt($('#quantity' + productId).val()) + 1;
        if (0 <= quantityInput && quantityInput <= parseInt($('#quantityAvaiable').text().replace(/,/g, ''))) {
            $.ajax({
                url: "/update-quantity",
                type: "get",
                data: {
                    productId: productId,
                    quantity: quantityInput
                },
                success: function(response){
                    $('#quantity' + productId).val(quantityInput);
                    $("#total-price-item" + productId).text(quantityInput * parseInt($("#price" + productId).text().replace(/,/g, '')));
                    calcTotalPrice();
                },
                error: function(xhr){
                    alert("An error occurred");
                }
            });
        }

    });
});