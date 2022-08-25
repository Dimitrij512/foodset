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
            window.location.href='/refugee/registration-infos/generate-pdf-file';
        });

        document.getElementById('date').value = new Date().toISOString().split('T')[0];

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
            $.get("/refugee/registration-infos-content/?receiveDate=" + selectedDate, function (response) {

                $(".reg-info-table-body").empty();
                response.forEach(function(refugeesInfo) {
                   let html =
                        '<tr>'
                        + '<td>' + refugeesInfo.date + '</td>'
                        + '<td>' + refugeesInfo.time + '</td>'
                        + '<td>' + refugeesInfo.phoneNumber + '</td>'
                        + '<td>' + refugeesInfo.surname + '</td>'
                        + '<td>' + refugeesInfo.name + '</td>'
                        + '<td>' + refugeesInfo.kidsCount + '</td>'
                        + '<td>' + refugeesInfo.typeSet + '</td>'
                        + '<td>' + refugeesInfo.isReceive + '</td>' +
                        "</tr>"
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
            <table class="table">
                <thead>
                <tr class="filters">
                    <th><input type="text" class="form-control" placeholder="Дата" disabled></th>
                    <th><input type="text" class="form-control" placeholder="Час" disabled></th>
                    <th><input type="text" class="form-control" placeholder="Телефон" disabled></th>
                    <th><input type="text" class="form-control" placeholder="Фамілія" disabled></th>
                    <th><input type="text" class="form-control" placeholder="Ім'я" disabled></th>
                    <th><input type="text" class="form-control" placeholder="Кільікість дітей" disabled></th>
                    <th><input type="text" class="form-control" placeholder="Тип допомоги" disabled></th>
                    <th><input type="text" class="form-control" placeholder="Видано" disabled></th>
                </tr>
                </thead>
                <tbody class="reg-info-table-body">
                <#list registrationInfos as registrationInfo>
                    <tr>
                        <td>${registrationInfo.date}</td>
                        <td>${registrationInfo.time}</td>
                        <td>${registrationInfo.phoneNumber}</td>
                        <td>${registrationInfo.surname}</td>
                        <td>${registrationInfo.name}</td>
                        <td>${registrationInfo.kidsCount}</td>
                        <td>${registrationInfo.typeSet}</td>
                        <td>${registrationInfo.isReceive}</td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>
</div>