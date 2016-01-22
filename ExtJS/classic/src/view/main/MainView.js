Ext.define('VPSServer.view.main.MainView', {
   extend: 'Ext.container.Container',

   requires: [
      'Ext.plugin.Viewport',
      'VPSServer.view.main.MainController',
      'VPSServer.view.login.LoginView',
      'VPSServer.view.fileslist.FilesView'
   ],
   views: ['formlogin', 'gridfiles'],

   controller: 'main',
   alias: 'view.main',

   items: [{
         xtype: 'formlogin',
         hidden: true,
         reference: 'formlogin'
      }, {
         xtype: 'gridfiles',
         hidden: true,
         reference: 'gridfiles'
      }
   ]
});