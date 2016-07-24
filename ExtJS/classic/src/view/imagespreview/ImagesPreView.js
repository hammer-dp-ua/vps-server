Ext.define('VPSServer.view.imagespreview.ImagesPreView', {
   extend: 'Ext.panel.Panel',

   requires: [
      'VPSServer.view.imagespreview.ImagesPreviewController',
      'VPSServer.model.FileModel',
      'VPSServer.store.ImageFilesStore'
   ],

   viewModel: {
      type: 'imagespreview'
   },
   /*store: {
      type: 'imageFilesStore'
   },*/
   controller: 'imagespreview',

   alias: 'widget.imagespreview',
   reference: 'imagesPreView',
   closable: true,

   items: Ext.create('Ext.view.View', {
      bind: {
         store: '{images}'
      },

      tpl: [
         '<tpl for=".">',
            '<div class="" id="{name:stripTags}">',
               '<div class=""><img src="{name}" title="{name:htmlEncode}"></div>',
               '<span class="">{shortName:htmlEncode}</span>',
            '</div>',
         '</tpl>',
         '<div class=""></div>'
      ],
      multiSelect: false,
      trackOver: true,
      overItemCls: 'x-item-over',
      itemSelector: 'div.thumb-wrap',
      emptyText: 'No images to display'
   }),

   show: function(invokedView, videoFileName) {
      this.setTitle(videoFileName);
      this.getController().loadAndUpdateData(invokedView, videoFileName,
         this.callParent); // Call the superclass "show" method
   }
});