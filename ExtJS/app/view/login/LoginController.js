Ext.define('VPSServer.view.login.LoginController', {
   extend: 'Ext.app.ViewController',

   alias: 'controller.login',

   onLogin: function() {
      var viewModelData = this.getViewModel().data;

      if (viewModelData.userId && viewModelData.password) {
         Ext.Ajax.request({
            url: '/video/rest/login',
            method: 'POST',
            params: {
               userId: viewModelData.userId,
               password: viewModelData.password,
               rememberMe: viewModelData.remember.checked
            },

            success: function(response, opts) {
               var expirationDate = new Date(),
                   currentYear = expirationDate.getYear(),
                   currentMonth = expirationDate.getMonth(); // 0-11

               if (currentMonth === 11) {
                  expirationDate.setMonth(0);
                  expirationDate.setYear(currentYear + 1);
               } else {
                  expirationDate.setMonth(currentMonth + 1);
               }

               Ext.util.Cookies.create(VPSServer.view.main.MainController.CREDENTIALS_COOKIE, '~~~', expirationDate);
               this.getView().hide();
               Ext.GlobalEvents.fireEvent('successfulLogin');

               this.lookupReference('wrongloginorpassword').show();
            },
            failure: function(response, opts) {
               Ext.Msg.alert('Error', response);
            }
         });
      }
   }
});