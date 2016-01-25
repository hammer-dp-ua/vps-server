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
   height: 'auto',
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

   dockedItems: [{
      xtype: 'label',
      dock: 'bottom',
      text: 'Wrong User or Password',
      hidden: true,
      reference: 'wrongloginorpassword',
      style: {
         color: 'red',
         'text-align': 'center',
         padding: '5px',
         'font-weight': 'bold'
      }
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
   }
});