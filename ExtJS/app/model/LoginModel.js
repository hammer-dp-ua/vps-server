Ext.define('VPSServer.model.LoginModel', {
   extend: 'Ext.data.Model',

   fields: [
      {name: 'userId', type: 'string'},
      {name: 'pass', type: 'string'}
   ],

   proxy: {
      type: 'rest',
      buildUrl: function(request) {
         return '/video/rest/login';
      }
   }
});
