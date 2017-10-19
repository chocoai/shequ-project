CKEDITOR.plugins.add('date', {
    requires: ['dialog'],
    init: function (a) {
        var b = a.addCommand('date', new CKEDITOR.dialogCommand('date'));
        a.ui.addButton('date', {
            label: a.lang.date.toolbar,
            command: 'date',
            icon: this.path + 'images/date.jpg'
        });
        CKEDITOR.dialog.add('date', this.path + 'dialogs/date.js');
    }
});