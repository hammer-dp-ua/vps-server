Ext.define('VPSServer.view.fullscreenimage.ImageView', {
   extend: 'Ext.window.Window',

   layout: 'fit',
   modal: true,
   width: 500,
   height: 500,
   closeToolText: null,

   html: '<img class="" src="mocks/video_file_name/001.jpeg">',

   items: [{
      xtype: 'button',
      text: '<'
   }, {
      xtype: 'button',
      text: '>'
   }]
});