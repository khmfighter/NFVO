/*
 * Copyright (c) 2016 Open Baton (http://www.openbaton.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.openbaton.nfvo.vim_interfaces.resource_management;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import org.openbaton.catalogue.mano.descriptor.VNFComponent;
import org.openbaton.catalogue.mano.descriptor.VirtualDeploymentUnit;
import org.openbaton.catalogue.mano.record.VNFCInstance;
import org.openbaton.catalogue.mano.record.VirtualNetworkFunctionRecord;
import org.openbaton.catalogue.nfvo.Quota;
import org.openbaton.catalogue.nfvo.Server;
import org.openbaton.catalogue.nfvo.VimInstance;
import org.openbaton.catalogue.security.Key;
import org.openbaton.exceptions.VimException;

/** Created by mpa on 30/04/15. */
public interface ResourceManagement {
  /**
   * This operation allows requesting the instantiation and assignment of a virtualised resource to
   * the VNF, as indicated by the consumer functional block.
   */
  Future<VNFCInstance> allocate(
      VimInstance vimInstance,
      VirtualDeploymentUnit vdu,
      VirtualNetworkFunctionRecord virtualNetworkFunctionRecord,
      VNFComponent vnfComponent,
      String userdata,
      Map<String, String> floatingIps,
      Set<Key> keys)
      throws VimException;

  /**
   * This operation allows querying a virtualised resource, i.e. retrieve information about an
   * instantiated virtualised resource.
   *
   * @param vimInstance
   */
  List<Server> queryResources(VimInstance vimInstance) throws VimException;

  /**
   * This operation allows updating the configuration and/or parameterization of an instantiated
   * virtualised resource.
   *
   * @param vdu
   */
  void update(VirtualDeploymentUnit vdu);

  /**
   * This operation allows scaling a virtualised resource by adding or removing capacity, e.g.
   * adding vCPUs to a virtual machine.
   *
   * @param vdu
   */
  void scale(VirtualDeploymentUnit vdu);

  /**
   * This operation allows moving virtualised resources between locations. For instance, the
   * operation performs the migration of a computing resource from one host to another host; while
   * for a storage resource, it migrates the resource from one storage location to another.
   *
   * @param vdu
   */
  void migrate(VirtualDeploymentUnit vdu);

  /**
   * This operation allows executing specific commands on certain allocated virtualised resources.
   * Examples on compute resources can include (but not limited to): start, stop, pause, suspend,
   * capture snapshot, etc.
   *
   * @param vdu
   * @param operation
   */
  void operate(VirtualDeploymentUnit vdu, String operation);

  /**
   * This operation allows de-allocating and terminating an instantiated virtualised resource. This
   * operation frees resources and returns them to the NFVI resource pool.
   *
   * @param vnfcInstance
   * @param vimInstance
   */
  Future<Void> release(VNFCInstance vnfcInstance, VimInstance vimInstance) throws VimException;

  /**
   * This operation allows requesting the reservation of a set of virtualised resources to a
   * consumer functional block without performing the steps of "Allocate Resource".
   *
   * @param vdu
   */
  void createReservation(VirtualDeploymentUnit vdu);

  /**
   * This operation allows querying an issued resources reservation, e.g. to discover the
   * virtualised resources included in a specific reserved resources pool, or the amount of free
   * resources in such a pool.
   */
  void queryReservation();

  /**
   * This operation allows updating an issued resources reservation to increase or decrease the
   * amount of virtualised resources in the reserved resources pool.
   *
   * @param vdu
   */
  void updateReservation(VirtualDeploymentUnit vdu);

  /**
   * This operation allows releasing an issued resources reservation, hence freeing the reserved
   * virtualised resources.
   *
   * @param vdu
   */
  void releaseReservation(VirtualDeploymentUnit vdu);

  /**
   * This operations return the maximal Quotas allowed to allocate.
   *
   * @return quota
   */
  Quota getQuota(VimInstance vimInstance) throws VimException;
}
