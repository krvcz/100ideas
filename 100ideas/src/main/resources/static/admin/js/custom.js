$('document').ready(function() {
    $('.table .edit').on('click', function(event){

       event.preventDefault();

    var href= $(this).attr('href');

    $.get(href, function(category, status) {
        $('#NameEdit').val(category.name);
        $('#IdEdit').val(category.id);
        });

    $.get(href, function(question, status) {
        $('#ContentEdit').val(question.content);
        $('#CategoryEdit').val(question.category.name);

        });

      $('#modal-edit').modal();
        });


    $('.card-header .add').on('click', function(event){

        event.preventDefault();
        $('#modal-add').modal();

    });

    $('.table .delete').on('click', function(event){

        event.preventDefault();
        var href= $(this).attr('href');
        if (confirm("Are you sure?") == true) {
            $('<form action=' + href +  ' method="post"><input type="hidden" name="name" value="value1"></input></form>').appendTo('body').submit().remove();
        }
    });
        // $.post(href, function(data, status){
        //
        // });

    });










