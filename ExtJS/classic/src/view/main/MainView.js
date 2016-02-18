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

   layout: 'border',

   items: [{
      xtype: 'window',
      region: 'center',
      hidden: true,
      header: false,
      reference: 'formlogin',
      items: [{
         xtype: 'formlogin'
      }]
   }, {
      xtype: 'gridfiles',
      region: 'center',
      hidden: true,
      reference: 'gridfiles'
   }, {
      region: 'west',
      width: '10%'
   }, {
      region: 'east',
      width: '10%'
   }]
});