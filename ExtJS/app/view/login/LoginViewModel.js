Ext.define('VPSServer.view.login.LoginViewModel', {
   extend: 'Ext.app.ViewModel',

   alias: 'viewmodel.login',

   data: {
      userId: null,
      password: null,
      remember: true
   }
});