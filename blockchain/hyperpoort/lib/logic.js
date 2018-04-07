'use strict';

/**
 * StatusUpdate transaction
 * @param {org.ebpi.hackathon.StatusUpdate} StatusUpdate
 * @transaction
 */
function doStatusUpdate(StatusUpdate) {
    return getAssetRegistry('org.ebpi.hackathon.Aanlevering')
        .then(function (AanleveringAssetRegistry) {
            return AanleveringAssetRegistry.get(StatusUpdate.Kenmerk);
        })
    .then(function(Aanlevering) {
        Aanlevering.status.push(StatusUpdate.newStatus)
        return getAssetRegistry('org.ebpi.hackathon.Aanlevering')
        .then(function (AanleveringAssetRegistry) {
            return AanleveringAssetRegistry.update(Aanlevering);
        })
    })
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
    var newID = Aanleveren.OntvangerId
    var factory = getFactory()
    var newAanlevering = factory.newResource('org.ebpi.hackathon', 'Aanlevering', newAanleverkenmerk)
    newAanlevering.Hash = newHash
    newAanlevering.status = []
    newAanlevering.status.push(newStatus)
    newAanlevering.Aanleveraar = factory.newRelationship('org.ebpi.hackathon', 'AanleverendePartij', newKvkNummer)
    newAanlevering.Ontvanger = factory.newRelationship('org.ebpi.hackathon', 'OntvangendePartij', newID)
    return getAssetRegistry('org.ebpi.hackathon.Aanlevering')
        .then(function (aanleveringRegistry) {
            return aanleveringRegistry.add(newAanlevering)

        })
}

/**
 * set final status
 * @param {org.ebpi.hackathon.SetFinalStatus} SetFinalStatus
 * @transaction
 */
function doSetFinalStatus(FinalStatus) {
    return getAssetRegistry('org.ebpi.hackathon.Aanlevering')
        .then(function (AanleveringAssetRegistry) {
            return AanleveringAssetRegistry.get(FinalStatus.Kenmerk);
        })
    .then(function(Aanlevering) {
        Aanlevering.status.push(FinalStatus.newStatus)
        return getAssetRegistry('org.ebpi.hackathon.Aanlevering')
        .then(function (AanleveringAssetRegistry) {
            return AanleveringAssetRegistry.update(Aanlevering);
        })
    })
}
