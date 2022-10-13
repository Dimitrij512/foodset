<link href="//netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<#--    <!-- Bootstrap Date-Picker Plugin &ndash;&gt;-->
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.12.4.js"></script>
<script src="//code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
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

        $('#generate-xls').click(function () {
            let selectedDate = $('#date').val();
            window.location.href='/registration-infos/generate-excel-file/?receiveDate='+ selectedDate;
        });

        $('#create-registration-info').click(function () {
            window.location.href='/registration-infos/create/free';
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

        $('#date').datepicker({
            showAnim: "fold",
            firstDay: 1,
            monthNames: ["Січень", "Лютий", "Березень", "Квітень", "Травень", "Червень", "Липень", "Серпень", "Вересень", "Жовтень", "Листопад", "Грудень"],
            dayNamesMin: ["Нд", "Пн", "Вт", "Ср", "Чт", "Пт", "Сб"],
            dateFormat: "yy-mm-dd",
            beforeShowDay: $.datepicker.noWeekends
        });

        $('#date').datepicker().change("changeDate", function() {
            let selectedDate = $('#date').val();
            $.get("/registration-infos-content/?receiveDate=" + selectedDate, function (response) {

                $(".reg-info-table-body").empty();
                response.forEach(function(refugeesInfo) {
                   let html =
                        '<tr>'
                        + '<td>' + refugeesInfo.receiveDate + '</td>'
                        + '<td>' + refugeesInfo.stream + '</td>'
                        + '<td>' + refugeesInfo.phoneNumber + '</td>'
                        + '<td>' + refugeesInfo.surname + '</td>'
                        + '<td>' + refugeesInfo.name + '</td>'
                        + '<td>' + refugeesInfo.kidsCount + '</td>'
                        + '<td>' + refugeesInfo.typeSet + '</td>'
                        + '<td class="is-receive-row">'
                              + '<select disabled class="is-receive-row-select">'
                                  + '<option selected>' + refugeesInfo.receive + '</option>'
                                  + '<option>Так</option>'
                                  + '<option>Ні</option>'
                              + '</select>'
                        + '</td>'
                        + '<td class="info-id" style="display: none">' + refugeesInfo.id + '</td>'
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
            let code = e.keyCode || e.which;
            if (code == '9') return;
            /* Useful DOM data and selectors */
            let $input = $(this),
                inputContent = $input.val().toLowerCase(),
                $panel = $input.parents('.filterable'),
                column = $panel.find('.filters th').index($input.parents('th')),
                $table = $panel.find('.table'),
                $rows = $table.find('tbody tr');
            /* Dirtiest filter function ever ;) */
            let $filteredRows = $rows.filter(function () {
                var value = $(this).find('td').eq(column).text().toLowerCase();
                return value.indexOf(inputContent) === -1;
            });
            $table.find('tbody .no-result').remove();
            $rows.show();
            $filteredRows.hide();
            if ($filteredRows.length === $rows.length) {
                $table.find('tbody').prepend($('<tr class="no-result text-center"><td colspan="' + $table.find('.filters th').length + '">Нічого не знайдено</td></tr>'));
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
                    <button class="btn btn-default btn-xs btn-generate-xls" id="generate-xls"><span class="glyphicon glyphicon-file"></span>
                        xls
                    </button>
                </div>
                <div class="pull-right" style="margin-right: 2px">
                    <button class="btn btn-default btn-xs btn-filter"><span class="glyphicon glyphicon-filter"></span>
                        Фільтр
                    </button>
                </div>

                <div class="pull-right" style="margin-right: 2px">
                    <button class="btn btn-default btn-xs" id="create-registration-info"><span class="glyphicon glyphicon-plus"></span>
                        Додати
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
                    <th><input type="text" class="form-control" placeholder="Члени сім'ї" disabled></th>
                    <th><input type="text" class="form-control" placeholder="Тип" disabled></th>
                    <th><input type="text" class="form-control" placeholder="Видано" disabled></th>
                </tr>
                </thead>
                <tbody class="reg-info-table-body">
                <#list registrationInfos as registrationInfo>
                    <tr>
                        <td>${registrationInfo.receiveDate}</td>
                        <td>${registrationInfo.stream}</td>
                        <td>${registrationInfo.phoneNumber}</td>
                        <td>${registrationInfo.surname}</td>
                        <td>${registrationInfo.name}</td>
                        <td>${registrationInfo.kidsCount}</td>
                        <td>${registrationInfo.typeSet}</td>
                        <td class='is-received-row'>
                            <select disabled  class="is-receive-row-select">
                                <option selected>${registrationInfo.receive}</option>
                                <option>Так</option>
                                <option>Ні</option>
                            </select>
                        </td>
                        <td class='info-id' style="display:none">${registrationInfo.id}</td>
                        <td><button id="edit" class="btn btn-default reg-info-row-edit" style= color:green><span class="glyphicon glyphicon-edit"></span></button></td>
                        <td><button id="save" class="btn btn-default reg-info-row-save" disabled><span class="glyphicon glyphicon-saved"></span></button></td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>
</div>