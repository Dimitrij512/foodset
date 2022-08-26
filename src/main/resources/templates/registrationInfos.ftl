<link href="//netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<#--    <!-- Bootstrap Date-Picker Plugin &ndash;&gt;-->
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/js/bootstrap-datepicker.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/css/bootstrap-datepicker3.css"/>
<!------ Include the above in your HEAD tag ---------->

<style>
    .filterable {
        margin-top: 15px;
    }

    .filterable .panel-heading .pull-right {
        margin-top: -20px;
    }

    .filterable .filters input[disabled] {
        background-color: transparent;
        border: none;
        cursor: auto;
        box-shadow: none;
        padding: 0;
        height: auto;
    }

    .filterable .filters input[disabled]::-webkit-input-placeholder {
        color: #333;
    }

    .filterable .filters input[disabled]::-moz-placeholder {
        color: #333;
    }

    .filterable .filters input[disabled]:-ms-input-placeholder {
        color: #333;
    }
</style>

<script>
    $(document).ready(function () {

        $('#generate-pdf').click(function () {
            let selectedDate = $('#date').val();
            window.location.href='/registration-infos/generate-pdf-file/?receiveDate='+ selectedDate;
        });

        document.getElementById('date').value = new Date().toISOString().split('T')[0];

        $('tbody').on('focus', 'button', function() {
            let saveButton = $(this).closest('tr').find('.reg-info-row-save');
            let editButton = $(this).closest('tr').find('.reg-info-row-edit');

            //edit
            if(saveButton.is(':disabled')) {
                $(this).closest('tr').find('.is-receive-row-select').prop("disabled", false)
                $(this).prop("disabled", true).css('color', 'gray');
                saveButton.prop("disabled", false).css('color', 'green');
            // save
            } else {
                let isReceive =  $(this).closest('tr').find('.is-receive-row-select').find(":selected").text();
                let registrationInfoId =  $(this).closest('tr').find('.info-id').text();

                $(this).closest('tr').find('.is-receive-row-select').prop("disabled", true);
                $(this).prop("disabled", true).css('color', 'gray');
                editButton.prop("disabled", false).css('color', 'green');

                $.ajax({
                    type: "PUT",
                    url: "/registration-infos/" + registrationInfoId,
                    accept: 'application/json',
                    contentType: 'application/json',
                    data: JSON.stringify({
                        received: "" + isReceive + "" ,
                        comment: ""
                    })
                });
            }
        });

        let date_input=$('input[name="date"]');
        let container=$('.bootstrap-iso').length>0 ? $('.bootstrap-iso').parent() : "body";
        date_input.datepicker({
            format: 'yyyy-mm-dd',
            container: container,
            todayHighlight: true,
            autoclose: true
        });

        $('#date').datepicker().on("changeDate", function() {
            let selectedDate = $('#date').val();
            $.get("/registration-infos-content/?receiveDate=" + selectedDate, function (response) {

                $(".reg-info-table-body").empty();
                response.forEach(function(refugeesInfo) {
                   let html =
                        '<tr>'
                        + '<td class="info-id" style="display: none">' + refugeesInfo.id + '</td>'
                        + '<td>' + refugeesInfo.receiveDate + '</td>'
                        + '<td>' + refugeesInfo.stream + '</td>'
                        + '<td>' + refugeesInfo.phoneNumber + '</td>'
                        + '<td>' + refugeesInfo.surname + '</td>'
                        + '<td>' + refugeesInfo.name + '</td>'
                        + '<td>' + refugeesInfo.kidsCount + '</td>'
                        + '<td>' + refugeesInfo.typeSet + '</td>'
                        // + '<td class="is-receive-row">' + refugeesInfo.isReceive + '</td>'
                        + '<td class="is-receive-row" id=  + refugeesInfo.id>'
                              + '<select disabled class="is-receive-row-select">'
                                  + '<option selected>' + refugeesInfo.receive + '</option>'
                                  + '<option>Так</option>'
                                  + '<option>Ні</option>'
                              + '</select>'
                        + '</td>'
                        + '<td><button id="edit" class="btn btn-default reg-info-row-edit" style="color:green;""><span class="glyphicon glyphicon-edit"></span></button></td>'
                        + '<td><button id="save" class="btn btn-default reg-info-row-save" disabled><span class="glyphicon glyphicon-saved"></span></button></td>' +
                        '</tr>';

                    $(".reg-info-table-body").append(html);
                });
            });
        });

        $('.filterable .btn-filter').click(function () {
            var $panel = $(this).parents('.filterable'),
                $filters = $panel.find('.filters input'),
                $tbody = $panel.find('.table tbody');
            if ($filters.prop('disabled') == true) {
                $filters.prop('disabled', false);
                $filters.first().focus();
            } else {
                $filters.val('').prop('disabled', true);
                $tbody.find('.no-result').remove();
                $tbody.find('tr').show();
            }
        });

        $('.filterable .filters input').keyup(function (e) {
            /* Ignore tab key */
            var code = e.keyCode || e.which;
            if (code == '9') return;
            /* Useful DOM data and selectors */
            var $input = $(this),
                inputContent = $input.val().toLowerCase(),
                $panel = $input.parents('.filterable'),
                column = $panel.find('.filters th').index($input.parents('th')),
                $table = $panel.find('.table'),
                $rows = $table.find('tbody tr');
            /* Dirtiest filter function ever ;) */
            var $filteredRows = $rows.filter(function () {
                var value = $(this).find('td').eq(column).text().toLowerCase();
                return value.indexOf(inputContent) === -1;
            });
            $table.find('tbody .no-result').remove();
            $rows.show();
            $filteredRows.hide();
            if ($filteredRows.length === $rows.length) {
                $table.find('tbody').prepend($('<tr class="no-result text-center"><td colspan="' + $table.find('.filters th').length + '">No result found</td></tr>'));
            }
        });
    });
</script>

<div class="container">
    <div class="row">
        <div class="panel panel-primary filterable">
            <div class="panel-heading">
                <h3 class="panel-title">Інформація про реєстрацію </h3>

                <div class="pull-right">
                    <button class="btn btn-default btn-xs btn-generate-pdf" id="generate-pdf"><span class="glyphicon glyphicon-file"></span>
                        pdf
                    </button>
                </div>
                <div class="pull-right" style="margin-right: 2px">
                    <button class="btn btn-default btn-xs btn-filter"><span class="glyphicon glyphicon-filter"></span>
                        Фільтр
                    </button>
                </div>

                <div class="pull-right input-group" style="margin-right: 2px">
                    <input class="btn btn-default btn-xs"  id="date" name="date" placeholder="дата" type="text">
                </div>

            </div>
            <table class="table" id="infos-table-id">
                <thead>
                <tr class="filters">
                    <th><input type="text" class="form-control" placeholder="Дата" disabled></th>
                    <th><input type="text" class="form-control" placeholder="Потік" disabled></th>
                    <th><input type="text" class="form-control" placeholder="Телефон" disabled></th>
                    <th><input type="text" class="form-control" placeholder="Фамілія" disabled></th>
                    <th><input type="text" class="form-control" placeholder="Ім'я" disabled></th>
                    <th><input type="text" class="form-control" placeholder="Діти" disabled></th>
                    <th><input type="text" class="form-control" placeholder="Тип" disabled></th>
                    <th><input type="text" class="form-control" placeholder="Видано" disabled></th>
                </tr>
                </thead>
                <tbody class="reg-info-table-body">
                <#list registrationInfos as registrationInfo>
                    <tr>
                        <td class="info-id" style="display: none">${registrationInfo.id}</td>
                        <td>${registrationInfo.receiveDate}</td>
                        <td>${registrationInfo.stream}</td>
                        <td>${registrationInfo.phoneNumber}</td>
                        <td>${registrationInfo.surname}</td>
                        <td>${registrationInfo.name}</td>
                        <td>${registrationInfo.kidsCount}</td>
                        <td>${registrationInfo.typeSet}</td>
                        <td class='is-received-row' id=${registrationInfo.id}>
                            <select disabled>
                                <option selected>${registrationInfo.receive}</option>
                                <option>Так</option>
                                <option>Ні</option>
                            </select>
                        </td>
                        <td><button class="btn btn-default reg-info-row"><span class="glyphicon glyphicon-edit"></span></button></td>
                        <td><button class="btn btn-default reg-info-row-save" disabled><span class="glyphicon glyphicon-saved"></span></button></td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>
</div>