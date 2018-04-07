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
        .then(function (aanleveringRegistry) {
            aanleveringRegistry.update(aangeleverdBericht)
        });
}

/**
 *  Aanleveren
 * @param {org.ebpi.hackathon.Aanleveren} Aanleveren
 * @transaction
 */
function doAanlevering(Aanleveren) {
    var newAanleveraar = getCurrentParticipant()
    var newKvkNummer = newAanleveraar.kvknummer
    var newStatus = 100
    var newAanleverkenmerk = Aanleveren.AanleverKenmerk
    var newHash = Aanleveren.Hash
    /*    return getParticipantRegistry('org.ebpi.hackathon.OntvangendePartij')
        .then(function (OntvangendePartijRegistry){
            var newOntvangendePartij = OntvangendePartijRegistry.get(Aanleveren.OntvangerId).then(function(value) { */
    var newID = Aanleveren.OntvangerId
    var factory = getFactory()
    var newAanlevering = factory.newResource('org.ebpi.hackathon', 'Aanlevering', newAanleverkenmerk)
    newAanlevering.Hash = newHash
    newAanlevering.status = newStatus
    newAanlevering.Aanleveraar = factory.newRelationship('org.ebpi.hackathon', 'AanleverendePartij', newKvkNummer)
    newAanlevering.Ontvanger = factory.newRelationship('org.ebpi.hackathon', 'OntvangendePartij', newID)
    return getAssetRegistry('org.ebpi.hackathon.Aanlevering')
        .then(function (aanleveringRegistry) {
            return aanleveringRegistry.add(newAanlevering)

        })
}