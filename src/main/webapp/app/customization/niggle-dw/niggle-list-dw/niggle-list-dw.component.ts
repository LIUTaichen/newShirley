import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { MatTableDataSource, MatSort, MatDialog } from '@angular/material';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { Niggle, Priority } from '../../../entities/niggle/niggle.model';
import { Plant } from '../../../entities/plant/plant.model';
import { NiggleRow } from './niggle-row.model';
import { NiggleService } from '../../../entities/niggle/niggle.service';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { Principal } from '../../../shared';
import { NiggleCreateDialogComponent } from './niggle-create-dialog/niggle-create-dialog.component';
import { NiggleEditDialogComponent } from './niggle-edit-dialog/niggle-edit-dialog.component';
import { PlantService } from '../../../entities/plant';
import { MaintenanceContractor, MaintenanceContractorService } from '../../../entities/maintenance-contractor';

@Component({
  selector: 'jhi-niggle-list-dw',
  templateUrl: './niggle-list-dw.component.html',
  styleUrls: ['./niggle-list-dw.component.css']
})
export class NiggleListDwComponent implements OnInit, OnDestroy {

  niggles: Niggle[];
  plants: Plant[];
  ownerOption = 'DEMPSEY';
  statusOption = ['OPEN', 'IN_PROGRESS', 'ON_HOLD', 'COMPLETED'];
  filter = '';
  maintenanceContractors: MaintenanceContractor[];
  idOfFocusedRow;
  displayedColumns = [
    'plantNumber',
    'orderNo',
    'quattraReference',
    'plantDescription',
    'location',
    'description',
    'status',
    'contractor',
    'quattraComments',
    'dateOpened',
    'daysOpened',
    'priorityOrder',
    'lastModifiedBy',
    'lastModifiedDate'
  ];
  dataSource: MatTableDataSource<NiggleRow>;

  @ViewChild(MatSort) sort: MatSort;

  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    private niggleService: NiggleService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private principal: Principal,
    public dialog: MatDialog,
    private plantService: PlantService,
    private maintenanceContractorService: MaintenanceContractorService,
  ) {
  }

  loadAll() {
    this.loadNiggles();
    this.plantService.query()
      .subscribe((res: HttpResponse<Plant[]>) => {
        const plants = res.body;
        this.plants = plants.sort((a: Plant, b: Plant) => {
          if (a.fleetId < b.fleetId) {
            return -1;
          } else if (a.fleetId > b.fleetId) {
            return 1;
          } else {
            return 0;
          }
        });
      }, (res: HttpErrorResponse) => this.onError(res.message));
    this.maintenanceContractorService.query()
      .subscribe((res: HttpResponse<MaintenanceContractor[]>) => {
        this.maintenanceContractors = res.body;
      }, (res: HttpErrorResponse) => this.onError(res.message));
  }

  loadNiggles() {
    this.niggleService.query().subscribe(
      (res: HttpResponse<Niggle[]>) => {
        this.niggles = res.body;
        this.prepareDataDource();
        this.applyFilter();
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  prepareDataDource() {
    this.dataSource = new MatTableDataSource(this.niggles.filter( (niggle) => this.needToShow(niggle), this).map( (niggle) => this.convertEntityToRow(niggle), this));
    this.dataSource.sort = this.sort;
  }

  ngOnInit() {
    this.loadAll();
    this.principal.identity().then((account) => {
      this.currentAccount = account;
    });
    this.registerChangeInNiggles();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: Niggle) {
    return item.id;
  }
  registerChangeInNiggles() {
    this.eventSubscriber = this.eventManager.subscribe('niggleListModification', (response) => this.loadNiggles());
  }

  private onError(error) {
    this.jhiAlertService.error(error.message, null, null);
  }

  applyFilter() {
    if (this.dataSource) {
      let filterValue = this.filter;
      filterValue = filterValue.trim(); // Remove whitespace
      filterValue = filterValue.toLowerCase(); // MatTableDataSource defaults to lowercase matches
      this.dataSource.filter = filterValue;
    }
  }

  getDaysOpened(niggle: Niggle) {
    if (niggle.dateOpened) {
      return Math.floor(Math.abs(niggle.dateOpened.getTime() - new Date().getTime()) / (1000 * 60 * 60 * 24));
    } else {
      return null;
    }
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(NiggleCreateDialogComponent, {
      width: '500px',
      panelClass: 'niggle-panel',
      data: {
        plants: this.plants,
        maintenanceContractors: this.maintenanceContractors
      }
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log('The dialog was closed');
      this.idOfFocusedRow = '';
      // this.animal = result;
    });
  }

  openEditDialog(id: number): void {
    this.idOfFocusedRow = id;
    const niggle: Niggle = this.niggles.find((niggleElement) => niggleElement.id === id);
    const dialogRef = this.dialog.open(NiggleEditDialogComponent, {
      width: '500px',
      panelClass: 'niggle-panel',
      data: {
        niggle,
        plants: this.plants,
        row: this.convertEntityToRow(niggle),
        maintenanceContractors: this.maintenanceContractors
      }
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log('The dialog was closed');
      // this.animal = result;
    });
  }

  convertEntityToRow(niggle: Niggle): NiggleRow {
    const niggleDaysOpened = this.getDaysOpened(niggle);
    let fleetId, plantDesctiption, siteAndName, location, locationUpdateTime, owner, contractor, orderNo;
    if (niggle.plant) {
      const plant: Plant = niggle.plant;
      fleetId = plant.fleetId;
      plantDesctiption = plant.description;
      siteAndName = plant.project ? plant.project['jobNumber'] + ' ' + plant.project['name'] : '';
      location = plant.location ? plant.location['address'] : '';
      locationUpdateTime = plant.location ? plant.location['timestamp'] : '';
      owner = plant.owner ? plant.owner['company'] : '';
    }
    contractor = niggle.assignedContractor ? niggle.assignedContractor['name'] : '';
    orderNo = niggle.purchaseOrder ? niggle.purchaseOrder['orderNumber'] : '';
    const priorityOrder: any = Priority[niggle.priority];
    const niggleRow: NiggleRow = {
      id: niggle.id,
      description: niggle.description,
      orderNo,
      status: niggle.status,
      note: niggle.note,
      priority: niggle.priority,
      priorityOrder,
      quattraReference: niggle.quattraReference,
      quattraComments: niggle.quattraComments,
      dateOpened: niggle.dateOpened,
      dateUpdated: niggle.lastModifiedDate,
      dateClosed: niggle.dateClosed,
      plantNumber: fleetId,
      plantDescription: plantDesctiption,
      site: siteAndName,
      location,
      locationUpdateTime,
      owner,
      contractor,
      daysOpened: niggleDaysOpened,
      createdBy: niggle.createdBy,
      createdDate: niggle.createdDate,
      lastModifiedBy: niggle.lastModifiedBy,
      lastModifiedDate: niggle.lastModifiedDate
    };
    return niggleRow;
  }

  getCount(): number {
    if (this.dataSource) {
      return this.dataSource.filteredData.length;
    } else {
      return 0;
    }
  }

  needToShow(niggle: Niggle): Boolean {
    const needToShowNiggleBasedOnOwner = this.needToShowNiggleBasedOnOwner(niggle);
    const needToShowNiggleBasedOnStatus = this.needToShowNiggleBasedOnStatus(niggle);
    return needToShowNiggleBasedOnOwner && needToShowNiggleBasedOnStatus;
  }

  needToShowNiggleBasedOnOwner(niggle: Niggle): Boolean {
    if (this.ownerOption !== 'ALL') {
      if (this.ownerOption === 'DEMPSEY') {
        if (niggle.plant['owner']['company'] !== 'Dempsey Wood Civil') {
          return false;
        }
      } else {
        if (niggle.plant['owner']['company'] === 'Dempsey Wood Civil') {
          return false;
        }
      }
    }
    return true;
  }

  needToShowNiggleBasedOnStatus(niggle: Niggle): Boolean {
    if (!this.statusOption || this.statusOption.length === 0) {
      return false;
    }
    if (this.statusOption.includes(niggle.status.toString())) {
      return true;
    }
    return false;
  }
}
