/**
 * This package contains the functionalities of the spaces on the baord.
 * ROAD - Take no action
 * PROPERTY NOT OWNED - The player may choose to purchase the property. They may choose not to purchase the property
 * and play passes to the next player.
 * PROPERTY OWNED - The player must pay rent to the owner of the property. If the property is owned by the current
 * player, no action is taken.
 * CONGESTION ZONE - If a player lands on a space in the congestion zone, then the player must pay a fine price based
 * on the journey undertaken.  The fine is equal to £10 * the value of the roll. If the player crosses a congestion
 * zone without landing in it, they are considered to have gone around the zone and they don’t have to pay.
 * COURT - If the player lands on a court space, they are considered to be attending for Jury Service. They will be
 * paid £100 for their time, but they will miss two turns.
 */
package com.cm6123.monopoly.spaces;

