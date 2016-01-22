Ext.define('VPSServer.view.login.LoginView', {
   extend: 'Ext.form.Panel',

   requires: [
      'VPSServer.view.login.LoginViewModel',
      'VPSServer.view.login.LoginController'
   ],

   alias: 'widget.formlogin',
   controller: 'login',
   viewModel: {
      type: 'login'
   },

   title: 'Login',
   frame: true,
   width: 320,
   bodyPadding: 10,

   items: [{
      xtype: 'textfield',
      allowBlank: false,
      fieldLabel: 'User ID',
      emptyText: 'user id',
      bind: '{userId}',
      reference: 'user'
   }, {
      xtype: 'textfield',
      allowBlank: false,
      fieldLabel: 'Password',
      emptyText: 'password',
      inputType: 'password',
      bind: '{password}',
      reference: 'pass'
   }, {
      xtype: 'checkboxfield',
      fieldLabel: 'Remember me',
      bind: '{remember}',
      reference : 'remember'
   }],

   buttons: [{
      id: 'loginbutton',
      text: 'Login',
      listeners: {
         click: 'onLogin'
      }
   }],

   initComponent: function() {
      this.defaults = {
         anchor: '100%',
         labelWidth: 120
      };

      this.callParent();
      this.center();
   }
});