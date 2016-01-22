Ext.define('VPSServer.view.main.MainController', {
   extend: 'Ext.app.ViewController',

   alias: 'controller.main',

   statics: {
      CREDENTIALS_COOKIE: "Credentials"
   },

   init: function() {
      if (!Ext.util.Cookies.get(this.self.CREDENTIALS_COOKIE)) {
         this.lookupReference('formlogin').show();
      } else {
         this.lookupReference('gridfiles').show();
      }
   }
});