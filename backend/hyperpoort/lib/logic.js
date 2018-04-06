'use strict';
/**
 * Write your transction processor functions here
 */

/**
 * Sample transaction
 * @param {org.ebpi.hackathon.UpdateStatus} UpdateStatus
 * @transaction
 */
function doUpdateStatus(UpdateStatus) {
    var aangeleverdBericht = UpdateStatus.aangeleverdBericht
    aangeleverdBericht.status = UpdateStatus.newStatus
    var statusRegistry;
    var myAanleverKenmerk = UpdateStatus.aangeleverdBericht.AanleverKenmerk;
    return getAssetRegistry('org.ebpi.hackathon.Aanlevering')
        .then(function(aanleveringRegistry) {
            aanleveringRegistry.update(aangeleverdBericht) 
        });
}