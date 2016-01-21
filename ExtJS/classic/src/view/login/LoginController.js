Ext.define('VPSServer.view.login.LoginController', {
   extend: 'Ext.app.ViewController',

   alias: 'controller.login',

   onLogin: function() {
      var viewModelData = this.getViewModel().data;

      //viewModelData.remember.checked
      if (viewModelData.user && viewModelData.password) {

      }
   }
});