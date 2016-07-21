Ext.define('VPSServer.Application', {
   extend: 'Ext.app.Application',

   name: 'VPSServer',

   launch: function () {
   },

   statics: {
      MOCKS_COOKIE: 'mocks'
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

function mocks(enable) {
   if (enable) {
      Ext.util.Cookies.set(VPSServer.Application.MOCKS_COOKIE, 1);
   } else {
      Ext.util.Cookies.set(VPSServer.Application.MOCKS_COOKIE, 0);
   }
}