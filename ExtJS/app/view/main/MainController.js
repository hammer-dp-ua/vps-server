Ext.define('VPSServer.view.main.MainController', {
   extend: 'Ext.app.ViewController',

   alias: 'controller.main',

   statics: {
      CREDENTIALS_COOKIE: 'Credentials',
      VIDEO_FILES_URI: '/vps-server/multimedia/rest/videoFiles/',
      IMAGE_FILES_URI: '/vps-server/multimedia/rest/imageFiles/',
      LOGIN_URI: '/vps-server/video/rest/login'
   },

   init: function() {
      if (Ext.util.Cookies.get(this.self.CREDENTIALS_COOKIE)) {
         this.lookupReference('formlogin').show();
      } else {
         this.lookupReference('gridfiles').show();
      }

      Ext.GlobalEvents.on('successfulLogin', this.successfulLogin, this);
      Ext.GlobalEvents.on('displayImageFiles', this.displayImageFiles, this);
   },

   successfulLogin: function() {
      this.lookupReference('formlogin').hide();
      this.lookupReference('gridfiles').show();
   },

   displayImageFiles: function(invokedView, videoFileName) {
      this.lookupReference('imagesPreView').show(invokedView, videoFileName);
   }
});