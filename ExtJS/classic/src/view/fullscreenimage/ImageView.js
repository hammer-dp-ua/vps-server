Ext.define('VPSServer.view.fullscreenimage.ImageView', {
   extend: 'Ext.window.Window',

   layout: 'fit',
   modal: true,
   width: '100%',
   height: '100%',
   closeToolText: null,

   style: {
      'background-position': 'center',
      'background-repeat': 'no-repeat',
      'background-size': 'contain',
      'background-image': 'url("mocks/video_file_name/001.jpeg")'
   },

   items: [{
      xtype: 'button',
      text: '<',
      cls: 'button-prev-image',
      focusCls: '',
      style: {
         'background-color': ''
      }
   }, {
      xtype: 'button',
      text: '>',
      cls: 'button-next-image',
      focusCls: ''
   }]
});