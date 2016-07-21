Ext.define('VPSServer.view.main.MainView', {
   extend: 'Ext.container.Container',

   requires: [
      'Ext.plugin.Viewport',
      'VPSServer.view.main.MainController',
      'VPSServer.view.login.LoginView',
      'VPSServer.view.fileslist.FilesView',
      'VPSServer.view.imagespreview.ImagesPreView'
   ],
   views: ['formlogin', 'gridfiles', 'imagespreview'],

   controller: 'main',
   alias: 'view.main',

   layout: 'border',

   items: [{
      xtype: 'container',
      region: 'center',
      layout: 'fit',
      items: [/*{
            xtype: 'window',
            hidden: true,
            header: false,
            reference: 'formlogin',
            items: [{
               xtype: 'formlogin'
            }]
         }, */{
            xtype: 'gridfiles',
            hidden: true,
            reference: 'gridfiles'
         }, {
            xtype: 'imagespreview',
            hidden: true,
            reference: 'imagesPreView'
         }]
   }, {
      region: 'west',
      width: '10%'
   }, {
      region: 'east',
      width: '10%'
   }]
});