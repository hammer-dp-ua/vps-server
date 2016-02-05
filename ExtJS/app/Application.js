Ext.define('VPSServer.Application', {
    extend: 'Ext.app.Application',
    
    name: 'VPSServer',

    //stores: ['VPSServer.store.FilesStore'],
    //models: ['VPSServer.model.LoginModel', 'VPSServer.model.FileModel'],
    //views: ['VPSServer.view.MainView', 'VPSServer.view.LoginView'],
    //controllers: ['VPSServer.controller.LoginController'],

    //Ext.ComponentQuery.query('formlogin')[0].center()

    //autoCreateViewport: 'VPSServer.view.main.MainView',
    
    launch: function () {
    },

    onAppUpdate: function () {
        Ext.Msg.confirm('Application Update', 'This application has an update, reload?',
            function (choice) {
                if (choice === 'yes') {
                    window.location.reload();
                }
            }
        );
    }
});
